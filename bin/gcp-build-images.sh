#!/bin/bash
# Automates the process of building system images on GCP.

PROJECT=ntw-sample-sys
ZONE=asia-southeast1-b
REGION=asia-southeast1
USER=jahong_liu

echo "-- Start Build Machine --"
gcloud compute instances start build-1 --project ${PROJECT} --zone ${ZONE}

echo "Wait for 60 seconds"
for i in `seq 1 30`; do
    sleep 2s
    printf .
done
echo ""

echo "-- Update Codebase --"
gcloud compute ssh ${USER}@build-1 --project ${PROJECT} --zone ${ZONE} -- 'cd /home/${USER}/End-to-End-Large-System-Evolution; git pull'

echo "-- Do Build --"
gcloud compute ssh ${USER}@build-1 --project ${PROJECT} --zone ${ZONE} -- 'cd /home/${USER}/End-to-End-Large-System-Evolution; ./build.sh'

echo "-- Do Build Clean --"
gcloud compute ssh ${USER}@build-1 --project ${PROJECT} --zone ${ZONE} -- 'cd /home/${USER}/End-to-End-Large-System-Evolution; ./build.sh clean all'

echo "-- Stop Build Machine --"
gcloud compute instances stop build-1 --project ${PROJECT} --zone ${ZONE}

echo "-- Delete Build Image --"
gcloud compute images delete --quiet minisys-build-1 --project ${PROJECT}

echo "-- Create New Image --"
gcloud compute images create minisys-build-1 --project=${PROJECT} --source-disk=build-1 --source-disk-zone=${ZONE} --storage-location=${REGION}
