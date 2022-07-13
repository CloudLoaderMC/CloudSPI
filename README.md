# Cloud Service Provider Interfaces

Service providers, and other global API-like elements,
separated so dependencies don't need to be on the core forge system.

* ```ml.cloudmc.api.distmarker.*``` are the ```Dist```/```OnlyIn``` annotation pair, used 
to identify elements that are only present in one of the two common distributions.
* ```ml.cloudmc.cloudspi.ICoreMod*``` are interfaces for communication between
Cloud and the coremod library.
