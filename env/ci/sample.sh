#!/bin/bash
# Anasibleは「クライアント側にインストールされていること」を前提にします。
ansible-playbook --private-key=xxx.pem -i hosts -u root main.yml
