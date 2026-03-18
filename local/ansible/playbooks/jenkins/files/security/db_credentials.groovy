import static com.cloudbees.plugins.credentials.CredentialsScope.GLOBAL
import com.cloudbees.plugins.credentials.domains.Domain
import com.cloudbees.plugins.credentials.SystemCredentialsProvider
import hudson.util.Secret
import org.jenkinsci.plugins.plaincredentials.impl.StringCredentialsImpl
import com.cloudbees.plugins.credentials.CredentialsStore

CredentialsStore credentialsStore = SystemCredentialsProvider.getInstance().getStore()

def sqlConnectionString = "{{ SQLConnectionString }}"

def sqlCredentials = new StringCredentialsImpl(
    GLOBAL,  
    "SQLConnectionString",  
    "SQL Server Connection String", 
    Secret.fromString(sqlConnectionString)
)
credentialsStore.addCredentials(Domain.global(), sqlCredentials)

println("SQL Connection String credential added successfully.")
