# Favalike

## Setup

* Make sure you have the environment variable `APPENGINE_HOME` set up. If you are working under MacOSX you have to
set the variable under `~/.MacOSX/environment.plist`:

    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE plist PUBLIC "-//Apple Computer//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
    <plist version="1.0">
       <dict>
          <key>APPENGINE_HOME</key>
          <string>/Users/johndoe/dev/tools/appengine-java-sdk-1.4.0</string>
       </dict>
    </plist>
    
* Run the application with `grails run-app`.

## Deployment

Due to a [bug](http://jira.codehaus.org/browse/GRAILSPLUGINS-1905) you have to set the Grails environment variable to
`production` when packaging the app.

    grails -Dgrails.env=production app-engine package
    appcfg.sh update ./target/war

