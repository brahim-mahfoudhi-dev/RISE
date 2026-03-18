
                        scp -i ~/.ssh/id_rsa ${PUBLISH_OUTPUT}/* vagrant@172.16.128.101:/vagrant/output-pipeline
                        ssh -i ~/.ssh/id_rsa vagrant@r172.16.128.101 "
                            export DOTNET_ENVIRONMENT=${DOTNET_ENVIRONMENT}                                    DOTNET_ConnectionStrings__SqlDatabase='${DOTNET_ConnectionStrings__SqlDatabase}' &&                             dotnet /vagrant/output-pipeline/Server.dll
                        "
                    