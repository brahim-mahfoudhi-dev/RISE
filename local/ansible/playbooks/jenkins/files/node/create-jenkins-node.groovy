import jenkins.model.Jenkins
import hudson.model.Node
import hudson.slaves.DumbSlave
import hudson.plugins.sshslaves.SSHLauncher
import hudson.slaves.RetentionStrategy

def credName = "{{ jenkins_credentials_id }}"
def nodeName = "{{ node_name }}"
def nodeHost = "{{ node_ip }}"

Node node = Jenkins.instance.nodes.find { it.nodeName == nodeName }
if (node) {
    println 'Node "' + nodeName + '" exists. Disconnecting first... '
    def computer = node.toComputer()
    computer.disconnect()
    
    def timeout = 5 * 60
    def waitInterval = 10
    while (computer.online) {
        println "Node [" + node.nodeName + "] is online. Waiting " + waitInterval + " seconds"
        sleep(waitInterval * 1000)
        timeout -= waitInterval
        if (timeout <= 0) {
            println "ERROR: Can't make the node offline"
            break
        }
    }
    println "Node disconnected. Removing node... "
    Jenkins.instance.removeNode(node)
    println "Node removed."
}

node = new DumbSlave(
    nodeName,
    nodeName + ' [Jenkins agent for building and deploying .NET applications]',
    '/var/lib/jenkins/agent',
    '1',
    Node.Mode.NORMAL,
    'App',
    new SSHLauncher(nodeHost, 22, credName),
    new RetentionStrategy.Always(),
    new LinkedList()
)

Jenkins.instance.addNode(node)
println 'Created node "' + nodeName + '".'
