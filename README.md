# Redis Clone in Java

In this repository, I’m building a Redis clone in Java that can handle basic commands such as:

- `PING` — Responds with `PONG`
- `SET key value` — Stores a value under the given key
- `GET key` — Retrieves the value associated with the key

This project explores low-level networking using sockets, custom protocol parsing (RESP), and in-memory key-value data storage.

## Goals

- Learn how Redis handles client communication and commands
- Work with raw TCP sockets in Java
- Build a minimal, testable Redis-like server from scratch

## Getting Started

To run the server locally:

# For Windows
```bash
./run.bat
```

# For Linux/MAC
```bash
./run.sh
```