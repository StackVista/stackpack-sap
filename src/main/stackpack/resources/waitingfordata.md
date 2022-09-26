### The StackState SAP StackPack is waiting for data

To begin collecting data from SAP, add the following configuration to StackState Agent V2:

1. Edit the Agent integration configuration file `/etc/stackstate-agent/conf.d/sap.d/conf.yaml` to include details of your SAP instance:
   - **host** - A unique name to identify the SAP instance.
   - **url** - The URL to connect to the SAP instance. Use http for basic authentication (user/pass) and https for client certificate authentication.
   - **user** and **pass** - SAP authentication details to use HTTP basic authentication. Requires an HTTP `url`. Use [secrets management](https://l.stackstate.com/ui-stackpack-secrets-management) to store passwords outside of the configuration file.
   - **cert** and **keyfile** - SAP authentication details to use client certificate authentication. Requires an HTTPS `url`.

   ```
   # Section used for global SAP check config
   init_config: {}
   
   instances:
       - host: TEST-01             # <sap_host_name> 
         url: https://test-01      # <sap_host_url>   
         user: test                # <username>
         pass: test                # <password>
         cert: /path/to/cert.pem   # <certificate_path>
         keyfile: /path/to/key.pem # <keyfile_path>
   ```

2. You can also add optional configuration, for details see the [SAP StackPack documentation \(docs.stackstate.com\)](https://l.stackstate.com/ui-sap-configure-check).
3. [Restart the StackState Agent\(s\)](https://l.stackstate.com/ui-stackpack-restart-agent) to apply the configuration changes.
4. Once the Agent has restarted, wait for the Agent to collect data from SAP and send it to StackState.

### Troubleshooting

Troubleshooting steps for any known issues can be found in the [StackState support Knowledge base](https://l.stackstate.com/ui-sap-support-kb).