import com.stackstate.stackpack.ProvisioningScript
import com.stackstate.stackpack.ProvisioningContext
import com.stackstate.stackpack.ProvisioningIO
import com.stackstate.stackpack.Version

class SAPProvision extends ProvisioningScript {

  SAPProvision(ProvisioningContext context) {
      super(context)
  }

  @Override
  ProvisioningIO<scala.Unit> install(Map<String, Object> config) {

    def templateArguments = [
                'topicName': topicName(config),
                'instanceId': context().instance().id(),
                'instanceName': config.sap_host
                ]
    templateArguments.putAll(config)

    return context().stackPack().importSnapshot("templates/sap.stj", [:]) >>
           context().instance().importSnapshot("templates/sap-instance-template.stj", templateArguments)
  }

  @Override
  ProvisioningIO<scala.Unit> upgrade(Map<String, Object> config, Version current) {
    return install(config)
  }

  @Override
  void waitingForData(Map<String, Object> config) {
    context().sts().onDataReceived(topicName(config), {
      context().sts().provisioningComplete()
    })
  }

  private def topicName(Map<String, Object> sapConfig) {
    def topic = sapConfig.sap_host
    return context().sts().createTopologyTopicName("sap", topic)
  }

}
