echo "Install Agile Integration Demo - Banking GUI"
echo

cd ../fisdemogui
oc new-project fisdemogui --display-name="Fuse Banking Demo - GUI" --description="Web GUI for Banking demo, does transfer and balance enquiry"
oc new-build --image-stream=nodejs --binary=true --name=fisdemogui
oc start-build fisdemogui --from-dir=. --follow
oc new-app fisdemogui
oc expose svc fisdemogui

