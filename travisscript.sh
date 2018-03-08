#!/bin/bash

for dir in $(ls -d1 -- */); do
	cd "$dir"
	mvn test
	cd ..
done

