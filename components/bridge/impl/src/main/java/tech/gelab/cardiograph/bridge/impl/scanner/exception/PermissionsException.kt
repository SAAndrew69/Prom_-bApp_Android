package tech.gelab.cardiograph.bridge.impl.scanner.exception

class PermissionsException(permissions: List<String>) :
    RuntimeException("Permissions not granted: $permissions")