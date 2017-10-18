#!/bin/bsah
# Anasibleは「クライアント側にインストールされていること」を前提にします。
ansible-playbook --private-key=XXX.pem -i hosts -u root main.yml
