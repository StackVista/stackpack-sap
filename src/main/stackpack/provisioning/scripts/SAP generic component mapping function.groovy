streams = new ArrayList();
checks = new ArrayList();
labels = new ArrayList();
layer = "urn:stackpack:common:layer:uncategorized"

switch (element.type.name) {
    case 'sap-host':
        layer = 'urn:stackpack:common:layer:machines'
        labels.addAll(['sap-host'])
        element.data.name = element.data.host
        streams.addAll([
            [
                name: "SAP Host Control state",
                conditions: [
                    [ "key": "tags.source_type_name", "value": "SAP:host control" ],
                    [ "key": "host", "value": element.data.host ],
                ],
                id: "-105",
                dataSource: "urn:stackpack:common:data-source:stackstate-generic-events"
            ],
            [
                name: "Free Space In Paging Files",
                metric: "SAP:FreeSpaceInPagingFiles",
                conditions: [
                    [ "key": "host", "value": element.data.host ],
                ],
                id: "-102",
                dataSource: "urn:stackpack:common:data-source:stackstate-metrics",
                streamType: "MetricStream",
                aggregation: "MIN",
                queryType: "MetricTelemetryQuery",
                dataType: "METRICS"
            ],
            [
                name: "Size Stored In Paging Files",
                metric: "SAP:SizeStoredInPagingFiles",
                conditions: [
                    [ "key": "host", "value": element.data.host ],
                ],
                id: "-103",
                dataSource: "urn:stackpack:common:data-source:stackstate-metrics",
                streamType: "MetricStream",
                aggregation: "MIN",
                queryType: "MetricTelemetryQuery",
                dataType: "METRICS"
            ],
            [
                name: "Total Swap Space Size",
                metric: "SAP:TotalSwapSpaceSize",
                conditions: [
                    [ "key": "host", "value": element.data.host ],
                ],
                id: "-104",
                dataSource: "urn:stackpack:common:data-source:stackstate-metrics",
                streamType: "MetricStream",
                aggregation: "MIN",
                queryType: "MetricTelemetryQuery",
                dataType: "METRICS"
            ]
        ])
        checks.addAll([
            ["name": "SAP Host Control health state", "streamId": "-105", "isSapCheck": true]
        ])
        break
    case 'sap-instance':
        layer = 'urn:stackpack:sap:shared:layer:sap-instances'
        labels.addAll(['sap-instance'])
        element.data.name = element.data.name +'-'+ element.data.system_number
        streams.addAll([
            [
                name: "SAP Host Instance state",
                conditions: [
                    [ "key": "tags.instance_id", "value": element.data.system_number ],
                    [ "key": "tags.source_type_name", "value": "SAP:host instance" ],
                    [ "key": "host", "value": element.data.host ],
                ],
                id: "-105",
                dataSource: "urn:stackpack:common:data-source:stackstate-generic-events"
            ],
            [
                name: "phys_memsize (MB)",
                metric: "phys_memsize",
                conditions: [
                    [ "key": "tags.instance_id", "value": element.data.system_number ],
                    [ "key": "host", "value": element.data.host ],
                ],
                id: "-101",
                dataSource: "urn:stackpack:common:data-source:stackstate-metrics",
                streamType: "MetricStream",
                aggregation: "MIN",
                queryType: "MetricTelemetryQuery",
                dataType: "METRICS"
            ],
            [
                name: "Database Connection",
                conditions: [
                    [ "key": "tags.instance_id", "value": element.data.system_number ],
                    [ "key": "tags.source_type_name", "value": "SAP:DatabaseConnection" ],
                    [ "key": "host", "value": element.data.host ],
                ],
                id: "-191",
                dataSource: "urn:stackpack:common:data-source:stackstate-generic-events"
            ]
        ])
        checks.addAll([
            ["name": "SAP Instance health state", "streamId": "-105", "isSapCheck": true],
            ["name": "Database Connection health state", "streamId": "-191", "isSapCheck": true],
        ])
        if (element.data.type.startsWith("ABAP")) {
            streams.addAll([
                [
                    name: "DIA_workers_free (Count)",
                    metric: "DIA_workers_free",
                    conditions: [
                        [ "key": "tags.instance_id", "value": element.data.system_number ],
                        [ "key": "host", "value": element.data.host ],
                    ],
                    id: "-102",
                    dataSource: "urn:stackpack:common:data-source:stackstate-metrics",
                    streamType: "MetricStream",
                    aggregation: "MIN",
                    queryType: "MetricTelemetryQuery",
                    dataType: "METRICS"
                ],
                [
                    name: "BTC_workers_free (Count)",
                    metric: "BTC_workers_free",
                    conditions: [
                        [ "key": "tags.instance_id", "value": element.data.system_number ],
                        [ "key": "host", "value": element.data.host ],
                    ],
                    id: "-103",
                    dataSource: "urn:stackpack:common:data-source:stackstate-metrics",
                    streamType: "MetricStream",
                    aggregation: "MIN",
                    queryType: "MetricTelemetryQuery",
                    dataType: "METRICS"
                ],
                [
                    name: "Response Time Dialog",
                    conditions: [
                      [ "key": "tags.instance_id", "value": element.data.system_number ],
                      [ "key": "tags.source_type_name", "value": "SAP:ResponseTimeDialog" ],
                      [ "key": "host", "value": element.data.host ],
                    ],
                    id: "-186",
                    dataSource: "urn:stackpack:common:data-source:stackstate-generic-events"
                ],
                [
                    name: "Spool Utilization",
                    conditions: [
                        [ "key": "tags.instance_id", "value": element.data.system_number ],
                        [ "key": "tags.source_type_name", "value": "SAP:Spool utilization" ],
                        [ "key": "host", "value": element.data.host ],
                    ],
                    id: "-187",
                    dataSource: "urn:stackpack:common:data-source:stackstate-generic-events"
                ],
                [
                    name: "Spool errors in Workers ",
                    conditions: [
                        [ "key": "tags.instance_id", "value": element.data.system_number ],
                        [ "key": "tags.source_type_name", "value": "SAP:ErrorsInWpSPO" ],
                        [ "key": "host", "value": element.data.host ],
                    ],
                    id: "-188",
                    dataSource: "urn:stackpack:common:data-source:stackstate-generic-events"
                ],
                [
                    name: "Spool error frequency in Workers",
                    conditions: [
                        [ "key": "tags.instance_id", "value": element.data.system_number ],
                        [ "key": "tags.source_type_name", "value": "SAP:ErrorsFreqInWpSPO" ],
                        [ "key": "host", "value": element.data.host ],
                    ],
                    id: "-189",
                    dataSource: "urn:stackpack:common:data-source:stackstate-generic-events"
                ],
                [
                    name: "Shortdump Health state",
                    conditions: [
                        [ "key": "tags.instance_id", "value": element.data.system_number ],
                        [ "key": "tags.source_type_name", "value": "SAP:ShortDumpFrequency" ],
                        [ "key": "host", "value": element.data.host ],
                    ],
                    id: "-190",
                    dataSource: "urn:stackpack:common:data-source:stackstate-generic-events"
                ]
            ])
            checks.addAll([
                ["name": "SAP Free Dialog Worker Count", "streamId": "-102", "isDIACheck": true],
                ["name": "SAP Free Background Worker Count", "streamId": "-103", "isBGCheck": true],
                ["name": "Dialog Response Time Health", "streamId": "-186", "isSapCheck": true],
                ["name": "Spool Health", "streamId": "-187", "isSapCheck": true],
                ["name": "Spool Errors Health", "streamId": "-188", "isSapCheck": true],
                ["name": "Spool Errors Frequency Health", "streamId": "-189", "isSapCheck": true],
            ])
            if (element.data.environment.contains("PRD")){
              checks.addAll([
                ["name": "Shortdump frequency Health", "streamId": "-190", "isSapCheck": true],
              ])
            }
        }

        break
    case 'sap-process':
        layer = 'urn:stackpack:common:layer:processes'
        labels.addAll(['sap-process'])
        streams.addAll([
            [
                name: "SAP Process state",
                conditions: [
                    [ "key": "tags.pid", "value": element.data.pid ],
                    [ "key": "tags.source_type_name", "value": "SAP:process state" ],
                    [ "key": "host", "value": element.data.host ],
                ],
                id: "-105",
                dataSource: "urn:stackpack:common:data-source:stackstate-generic-events"
            ]
        ])
        checks.addAll([
            ["name": "SAP process health state", "streamId": "-105", "isSapCheck": true]
        ])
        break
    case 'sap-database':
        layer = 'urn:stackpack:common:layer:databases'
        labels.addAll(['sap-database'])
        streams.addAll([
            [
                name: "SAP Database state",
                conditions: [
                    [ "key": "tags.database_name", "value": element.data.name ],
                    [ "key": "tags.source_type_name", "value": "SAP:database state" ],
                    [ "key": "host", "value": element.data.host ],
                ],
                id: "-105",
                dataSource: "urn:stackpack:common:data-source:stackstate-generic-events"
            ]
        ])
        checks.addAll([
            ["name": "SAP Database health state", "streamId": "-105", "isSapCheck": true]
        ])
        if (element.data.type.contains("ora")){
            streams.addAll([
                [
                    name: "DB Locks Health state",
                    conditions: [
                        [ "key": "tags.database", "value": element.data.name ],
                        [ "key": "tags.source_type_name", "value": "ORA:TableSpaceStatus" ],
                        [ "key": "host", "value": element.data.host ],
                    ],
                    id: "-201",
                    dataSource: "urn:stackpack:common:data-source:stackstate-generic-events"
                ],
                [
                    name: "Free Table Space",
                    metric: "ORA:FreeTableSpace",
                    conditions: [
                        [ "key": "host", "value": element.data.host ],
                        [ "key": "tags.database", "value": element.data.name ],
                    ],
                    id: "-212",
                    dataSource: "urn:stackpack:common:data-source:stackstate-metrics",
                    streamType: "MetricStream",
                    aggregation: "MIN",
                    queryType: "MetricTelemetryQuery",
                    dataType: "METRICS"
                ]
            ])
            checks.addAll([
                ["name": "DB Locks Health state", "streamId": "-201", "isSapCheck": true]
            ])
        }
        if (element.data.type.contains("sap")){
            streams.addAll([
                [
                    name: "Used Data Area",
                    metric: "MAXDB:UsedDataArea",
                    conditions: [
                        [ "key": "host", "value": element.data.host ],
                        [ "key": "tags.database", "value": element.data.name ],
                    ],
                    id: "-207",
                    dataSource: "urn:stackpack:common:data-source:stackstate-metrics",
                    streamType: "MetricStream",
                    aggregation: "MIN",
                    queryType: "MetricTelemetryQuery",
                    dataType: "METRICS"
                ],
                [
                    name: "Used Log Area",
                    metric: "MAXDB:UsedLogArea",
                    conditions: [
                        [ "key": "host", "value": element.data.host ],
                        [ "key": "tags.database", "value": element.data.name ],
                    ],
                    id: "-208",
                    dataSource: "urn:stackpack:common:data-source:stackstate-metrics",
                    streamType: "MetricStream",
                    aggregation: "MIN",
                    queryType: "MetricTelemetryQuery",
                    dataType: "METRICS"
                ]
            ])
        }
        if (element.data.type.contains("syb")){
            streams.addAll([
                [
                    name: "License Expiring (Days)",
                    metric: "SYB:LicenseExpiringDays",
                    conditions: [
                        [ "key": "host", "value": element.data.host ],
                        [ "key": "tags.database", "value": element.data.name ],
                    ],
                    id: "-213",
                    dataSource: "urn:stackpack:common:data-source:stackstate-metrics",
                    streamType: "MetricStream",
                    aggregation: "MIN",
                    queryType: "MetricTelemetryQuery",
                    dataType: "METRICS"
                ]
            ])
        }
        
        if (element.data.type.contains("hdb")){
            streams.addAll([
                [
                    name: "Backup Exists",
                    conditions: [
                        [ "key": "tags.database", "value": element.data.name ],
                        [ "key": "tags.source_type_name", "value": "HDB:BackupExist" ],
                        [ "key": "host", "value": element.data.host ],
                    ],
                    id: "-202",
                    dataSource: "urn:stackpack:common:data-source:stackstate-generic-events"
                ],
                [
                    name: "Recent Backup",
                    conditions: [
                        [ "key": "tags.database", "value": element.data.name ],
                        [ "key": "tags.source_type_name", "value": "HDB:RecentBackup" ],
                        [ "key": "host", "value": element.data.host ],
                    ],
                    id: "-203",
                    dataSource: "urn:stackpack:common:data-source:stackstate-generic-events"
                ],
                [
                    name: "Recent Log Backup",
                    conditions: [
                        [ "key": "tags.database", "value": element.data.name ],
                        [ "key": "tags.source_type_name", "value": "HDB:RecentLogBackup" ],
                        [ "key": "host", "value": element.data.host ],
                    ],
                    id: "-204",
                    dataSource: "urn:stackpack:common:data-source:stackstate-generic-events"
                ],
                [
                    name: "System Backup",
                    conditions: [
                        [ "key": "tags.database", "value": element.data.name ],
                        [ "key": "tags.source_type_name", "value": "HDB:SystemBackupExists" ],
                        [ "key": "host", "value": element.data.host ],
                    ],
                    id: "-205",
                    dataSource: "urn:stackpack:common:data-source:stackstate-generic-events"
                ],
                [
                    name: "System Replication",
                    conditions: [
                        [ "key": "tags.database", "value": element.data.name ],
                        [ "key": "tags.source_type_name", "value": "HDB:SystemReplication" ],
                        [ "key": "host", "value": element.data.host ],
                    ],
                    id: "-206",
                    dataSource: "urn:stackpack:common:data-source:stackstate-generic-events"
                ],
                [
                    name: "Delta Merges",
                    metric: "HDB:DeltaMerges",
                    conditions: [
                        [ "key": "host", "value": element.data.host ],
                        [ "key": "tags.database", "value": element.data.name ],                        
                    ],
                    id: "-209",
                    dataSource: "urn:stackpack:common:data-source:stackstate-metrics",
                    streamType: "MetricStream",
                    aggregation: "MIN",
                    queryType: "MetricTelemetryQuery",
                    dataType: "METRICS"
                ],
                [
                    name: "License Expiring (Days)",
                    metric: "HDB:LicenseExpiringDays",
                    conditions: [
                        [ "key": "host", "value": element.data.host ],
                        [ "key": "tags.database", "value": element.data.name ],                        
                    ],
                    id: "-210",
                    dataSource: "urn:stackpack:common:data-source:stackstate-metrics",
                    streamType: "MetricStream",
                    aggregation: "MIN",
                    queryType: "MetricTelemetryQuery",
                    dataType: "METRICS"
                ],
                [
                    name: "Last Backup (Days)",
                    metric: "HDB:LastBackupDays",
                    conditions: [
                        [ "key": "host", "value": element.data.host ],
                        [ "key": "tags.database", "value": element.data.name ],                        
                    ],
                    id: "-211",
                    dataSource: "urn:stackpack:common:data-source:stackstate-metrics",
                    streamType: "MetricStream",
                    aggregation: "MIN",
                    queryType: "MetricTelemetryQuery",
                    dataType: "METRICS"
                ]
            ])
            checks.addAll([
                ["name": "Backup Exists Health state", "streamId": "-202", "isSapCheck": true],
                ["name": "Recent Backup Health state", "streamId": "-203", "isSapCheck": true],
                ["name": "Recent Log Backup Health state", "streamId": "-204", "isSapCheck": true],
                ["name": "System Backup Health state", "streamId": "-205", "isSapCheck": true],
                ["name": "System Replication Health state", "streamId": "-206", "isSapCheck": true],
            ])
        }
        break
    case 'sap-database-component':
        layer = 'urn:stackpack:sap:shared:layer:sap-database-components'
        labels.addAll(['sap-database-component'])
        streams.addAll([
            [
                name: "SAP Database Component state",
                conditions: [
                    [ "key": "tags.database_component_name", "value": element.data.name ],
                    [ "key": "tags.source_type_name", "value": "SAP:database component state" ],
                    [ "key": "tags.database_name", "value": element.data.database_name ],
                    [ "key": "host", "value": element.data.host ],
                ],
                id: "-200",
                dataSource: "urn:stackpack:common:data-source:stackstate-generic-events"
            ]
        ])
        checks.addAll([
            ["name": "SAP DB Component health state", "streamId": "-200", "isSapCheck": true]
        ])
        break
        
    case 'sap-cloud-connector':
        layer = 'urn:stackpack:sap:shared:layer:sap-instances'
        labels.addAll(['sap-cloud-connector'])
        streams.addAll([
            [
                name: "SAP Cloud Connector state",
                conditions: [
                    [ "key": "tags.source_type_name", "value": "SAP:scc state" ],
                    [ "key": "host", "value": element.data.host ],
                ],
                id: "-195",
                dataSource: "urn:stackpack:common:data-source:stackstate-generic-events"
            ]
        ])
        checks.addAll([
            ["name": "SAP Cloud Connector health state", "streamId": "-195", "isSapCheck": true]
        ])
        
        break
    case 'sap-scc-subaccount':
        layer = 'urn:stackpack:sap:shared:layer:sap-instances'
        labels.addAll(['sap-scc-subaccount'])
        streams.addAll([
            [
                name: "SAP Cloud Connector Subaccount state",
                conditions: [
                    [ "key": "tags.source_type_name", "value": "SAP:scc subaccount state" ],
                    [ "key": "host", "value": element.data.host ],
                    [ "key": "tags.subaccount_name", "value": element.data.name ],
                    
                ],
                id: "-196",
                dataSource: "urn:stackpack:common:data-source:stackstate-generic-events"
            ]
        ])
        checks.addAll([
            ["name": "SAP Cloud Connector Subaccount health state", "streamId": "-196", "isSapCheck": true]
        ])
        
        break        
    default:
        result = 'Default'
        break
}
element.data.put('streams', streams)
element.data.put('checks', checks)
element.data.put('layer', layer)
element.data.put('labels', labels)
if (!element.data.domain){
    element.data.put("domain", "SAP")
}
if (!element.data.environment){
    element.data.put("environment", "SAP")
}

element
