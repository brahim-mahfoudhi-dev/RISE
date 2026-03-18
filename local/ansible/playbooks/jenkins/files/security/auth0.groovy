import static com.cloudbees.plugins.credentials.CredentialsScope.GLOBAL
import com.cloudbees.plugins.credentials.domains.Domain
import com.cloudbees.plugins.credentials.SystemCredentialsProvider
import hudson.util.Secret
import org.jenkinsci.plugins.plaincredentials.impl.StringCredentialsImpl
import com.cloudbees.plugins.credentials.CredentialsStore

CredentialsStore credentialsStore = SystemCredentialsProvider.getInstance().getStore()

def m2mClientId = "{{ M2MClientId }}"
def m2mClientSecret = "{{ M2MClientSecret }}"
def blazorClientId = "{{ BlazorClientId }}"
def blazorClientSecret = "{{ BlazorClientSecret }}"

// Add M2M Client ID credentials
def m2mCredentials = new StringCredentialsImpl(
    GLOBAL,  
    "M2MClientId",  
    "M2M Client ID", 
    Secret.fromString(m2mClientId)
)
credentialsStore.addCredentials(Domain.global(), m2mCredentials)

// Add M2M Client Secret credentials
def m2mSecretCredentials = new StringCredentialsImpl(
    GLOBAL,  
    "M2MClientSecret",  
    "M2M Client Secret", 
    Secret.fromString(m2mClientSecret)
)
credentialsStore.addCredentials(Domain.global(), m2mSecretCredentials)

// Add Blazor Client ID credentials
def blazorCredentials = new StringCredentialsImpl(
    GLOBAL,  
    "BlazorClientId",  
    "Blazor Client ID", 
    Secret.fromString(blazorClientId)
)
credentialsStore.addCredentials(Domain.global(), blazorCredentials)

def blazorSecretCredentials = new StringCredentialsImpl(
    GLOBAL,  
    "BlazorClientSecret",  
    "Blazor Client Secret", 
    Secret.fromString(blazorClientSecret)
)
credentialsStore.addCredentials(Domain.global(), blazorSecretCredentials)

println("Credentials added successfully.")
