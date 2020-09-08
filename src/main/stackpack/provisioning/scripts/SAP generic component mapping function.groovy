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
                    [ "key": "host", "value": element.data.host ]
                ],
                id: "-105",
                dataSource: "urn:stackpack:common:data-source:stackstate-generic-events"
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
                    [ "key": "host", "value": element.data.host ]
                ],
                id: "-105",
                dataSource: "urn:stackpack:common:data-source:stackstate-generic-events"
            ],
            [
                name: "phys_memsize (MB)",
                metric: "phys_memsize",
                conditions: [
                    [ "key": "tags.instance_id", "value": element.data.system_number ],
                    [ "key": "host", "value": element.data.host ]
                ],
                id: "-101",
                dataSource: "urn:stackpack:common:data-source:stackstate-metrics",
                streamType: "MetricStream",
                aggregation: "MIN",
                queryType: "MetricTelemetryQuery",
                dataType: "METRICS"
            ]
        ])
        checks.addAll([
            ["name": "SAP Instance health state", "streamId": "-105", "isSapCheck": true]
        ])
        if (element.data.type.startsWith("ABAP")) {
            streams.addAll([
                [
                    name: "DIA_workers_free (Count)",
                    metric: "DIA_workers_free",
                    conditions: [
                        [ "key": "tags.instance_id", "value": element.data.system_number ],
                        [ "key": "host", "value": element.data.host ]
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
                        [ "key": "host", "value": element.data.host ]
                    ],
                    id: "-103",
                    dataSource: "urn:stackpack:common:data-source:stackstate-metrics",
                    streamType: "MetricStream",
                    aggregation: "MIN",
                    queryType: "MetricTelemetryQuery",
                    dataType: "METRICS"
                ]
            ])
            checks.addAll([
                ["name": "SAP Free Dialog Worker Count", "streamId": "-102", "isDIACheck": true],
                ["name": "SAP Free Background Worker Count", "streamId": "-103", "isBGCheck": true]
            ])
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
                    [ "key": "host", "value": element.data.host ]
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
                    [ "key": "host", "value": element.data.host ],
                ],
                id: "-105",
                dataSource: "urn:stackpack:common:data-source:stackstate-generic-events"
            ]
        ])
        checks.addAll([
            ["name": "SAP DB Component health state", "streamId": "-105", "isSapCheck": true]
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
