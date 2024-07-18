package tech.gelab.cardiograph.bridge.impl.exception

class IllegalEventID(id: Int) : ParseException("Got illegal event id: $id")