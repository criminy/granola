# granola-current #

## What is it? ##

Granola is a java web framework inspired by a handful of existing Java and Python web frameworks.

## How does it work? ##

Granola is a container agnostic library for writing web code. It is split into a few major sub-systems: 

  * a routing system
  * an annotation and meta-programming API
  * a model-view-controller API
    * Includes template processing, request and response objects. 
  * a configuration system
    * Includes web.xml configuration.

Internally, granola is an HTTP routing component coupled with a simple annotation meta-programming system.
The additional functionality is built on top of these two components.

