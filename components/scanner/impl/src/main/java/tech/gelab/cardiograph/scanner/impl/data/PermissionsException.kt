package tech.gelab.cardiograph.scanner.impl.data

class PermissionsException(permissions: List<String>) :
    RuntimeException("Permissions not granted: $permissions")