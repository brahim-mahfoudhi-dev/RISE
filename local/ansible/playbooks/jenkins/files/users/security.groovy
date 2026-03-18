import hudson.security.*
import jenkins.model.*
import hudson.security.csrf.DefaultCrumbIssuer

def instance = Jenkins.getInstance()
def hudsonRealm = new HudsonPrivateSecurityRealm(false)
instance.setCrumbIssuer(new DefaultCrumbIssuer(true))
instance.save()

def existingUsers = hudson.model.User.getAll().collect { it.getId() }
println "Existing users: ${existingUsers.join(', ')}"

def jenkinsUsername = "{{ JENKINS_USERNAME }}"
def jenkinsPassword = "{{ JENKINS_PASSWORD }}"

if (existingUsers.contains(jenkinsUsername)) {
    def user = hudson.model.User.get(jenkinsUsername)
    user.setPassword(jenkinsPassword)
    user.save()
} else {
    def user = hudsonRealm.createAccount(jenkinsUsername, jenkinsPassword)
    if (user) {
        instance.setSecurityRealm(hudsonRealm)

        def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
        instance.setAuthorizationStrategy(strategy)
        instance.save()
    } 
}
