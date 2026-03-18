import hudson.security.*
import jenkins.model.*
import hudson.security.csrf.DefaultCrumbIssuer

def instance = Jenkins.getInstance()
def hudsonRealm = new HudsonPrivateSecurityRealm(false)
def users = hudsonRealm.getAllUsers()
instance.setCrumbIssuer(new DefaultCrumbIssuer(true))
instance.save()

def existingUsers = users.collect { it.toString() }
println "Existing users: ${existingUsers.join(', ')}" 

def jenkinsUsername = "{{ JENKINS_USERNAME }}"
def jenkinsPassword = "{{ JENKINS_PASSWORD }}"

if (existingUsers.contains(jenkinsUsername)) {
    println "Admin user already exists - updating password"
    def user = hudson.model.User.get(jenkinsUsername)
    user.setPassword(jenkinsPassword)
    user.save()
} else {
    println "--> Creating local admin user"
    hudsonRealm.createAccount(jenkinsUsername, jenkinsPassword)
    instance.setSecurityRealm(hudsonRealm)

    def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
    instance.setAuthorizationStrategy(strategy)
    instance.save()
}
