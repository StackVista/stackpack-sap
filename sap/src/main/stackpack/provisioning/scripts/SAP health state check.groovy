def event = events[-1]
def tags = event.point.getStructOrEmpty("tags")
Optional<String> stateOpt = tags.getString("status")


if (!stateOpt.isPresent()) {
    context.log.debug("state field is not present in the event's tags.")
    return ["healthState": Optional.empty()]
}
context.log.debug("Working with state: " + stateOpt)

def healthState = stateOpt
    .map { state -> switch(state.toLowerCase()) {
        case "sapcontrol-green": // fall through
        case "sap-host-instance-success":
        case "sap-host-control-success":
        case "saphostcontrol-db-running": return CLEAR

        case "sapcontrol-yellow": // fall through
        case "saphostcontrol-db-warning": return DEVIATING

        case "sapcontrol-red": //fall through
        case "sap-host-instance-error":
        case "sap-host-control-error":
        case "saphostcontrol-db-error": return CRITICAL
        
        default: return UNKNOWN
    } }.get()

context.log.debug("Switching state to: " + healthState)

return Optional.of(healthState)
