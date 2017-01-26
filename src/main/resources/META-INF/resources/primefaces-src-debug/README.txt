This library folder constains source code copy pasted from primefaces primefaces-6.0-sources.jar.
The source code can be temproarily injected to the an HTML page to override the minified javascript from primefaces.
Allowing us to debug unexpected behavior, when needed.

Include script for example with:
<h:outputScript library="primefaces-src-debug" name="core/core.ajax.js" target="body"/>