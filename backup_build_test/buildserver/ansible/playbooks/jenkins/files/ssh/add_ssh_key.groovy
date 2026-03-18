import jenkins.model.*
import hudson.security.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.impl.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey

def instance = Jenkins.getInstance()

def credentialId = "{{SSH_CREDENTIAL_ID}}"
def credentialDescription = "{{SSH_CREDENTIAL_DESCRIPTION}}"
def sshUsername = "{{SSH_USERNAME}}"
def privateKey = "{{SSH_PRIVATE_KEY}}"
def passphrase = "{{SSH_PASSPHRASE}}"

def privateKeySource = new BasicSSHUserPrivateKey.DirectEntryPrivateKeySource(new File(privateKey).text)

def sshKey = new BasicSSHUserPrivateKey(
    CredentialsScope.GLOBAL,
    credentialId,
    sshUsername,
    privateKeySource,
    passphrase,
    credentialDescription
)

def domain = Domain.global()
def store = CredentialsProvider.lookupStores(instance).iterator().next()

def existingCredential = store.getCredentials(domain).find { it.id == credentialId }
if (existingCredential) {
    store.removeCredentials(domain, existingCredential)
}

store.addCredentials(domain, sshKey)

instance.save()

println "SSH key credential '${credentialId}' added successfully."
