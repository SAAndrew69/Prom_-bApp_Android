package tech.gelab.cardiograph.pairing.impl

interface PairingFeatureEvent {
    data object NavigateConnection : PairingFeatureEvent
    data object NavigateMainScreen : PairingFeatureEvent
    data object PopBackStack : PairingFeatureEvent
//    data object ConnectionSucceed : PairingFeatureEvent
//    data object ConnectionFailed : PairingFeatureEvent
}