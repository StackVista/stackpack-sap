## The StackState SAP StackPack is installed

Congratulations! The SAP StackPack is configured correctly. Data is being received.

### What's next?

Topology from this SAP instance can be found in the following views:

- **SAP - Databases - {{configurationConfig.sap_host}}** - All SAP databases.
- **SAP - Instances - {{configurationConfig.sap_host}}** - All SAP instances, hosts and processes.
- **SAP - Uncategorized - {{configurationConfig.sap_host}}** - Any SAP components that are not databases, hosts, instances or processes.

For full details of the SAP integration, including the data retrieved, see the [SAP StackPack documentation \(docs.stackstate.com\)](https://l.stackstate.com/ui-sap-stackpack-docs).

### Change the StackState Agent SAP check configuration

Refer to the StackState docs for details on [how to change configuration of the StackState Agent SAP check](https://l.stackstate.com/ui-sap-configure-check) that collects data from SAP.

### Troubleshooting

Troubleshooting steps for any known issues can be found in the [StackState support Knowledge base](https://l.stackstate.com/ui-sap-support-kb).

### Uninstall the SAP StackPack

To remove this instance of the SAP, click the **UNINSTALL** button. This will remove all configuration for the {{configurationConfig.sap_host}} SAP instance in StackState.


