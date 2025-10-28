FROM ubuntu:latest
LABEL authors="tlege"

ENTRYPOINT ["top", "-b"]