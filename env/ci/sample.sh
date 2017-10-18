#!/bin/bash
# Anasibleは「クライアント側にインストールされていること」を前提にします。
ansible-playbook --private-key=~/Dropbox/keys/AWS/key/first_key.pem -i hosts -u ec2-user main.yml
