#!/bin/bash
while true; do
  if ! pgrep -f "node.*server.js" > /dev/null; then
    node /app/applet/server.js >> /var/log/server.log 2>&1
  fi
  sleep 3
done
