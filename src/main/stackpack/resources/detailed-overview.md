#### Prerequisites

The following prerequisites need to be met:

* StackState Agent V2 must be installed on a single machine which can connect to SAP Instance and StackState. (See the [StackState Agent V2 StackPack](/#/stackpacks/stackstate-agent-v2/) for more details)
* A SAP instance must be running.


#### Enabling SAP check
To enable the SAP check which collects the data from SAP host instance:

Edit the `conf.yaml` file in your agent `conf.d/sap.d` directory, replacing `<sap_host_name>` and `<sap_host_url>` with the unique name of SAP host to identify and URL to connect from your SAP instance.

```
# Section used for global SAP check config
init_config: {}

instances:
    - host: <sap_host_name>
      url: <sap_host_url>
      user: <username>
      pass: <password>
    
```

To publish the configuration changes, restart the StackState Agent(s).

Once the Agent is restarted, wait for the Agent to collect the data and send it to StackState.
