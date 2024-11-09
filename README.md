# CommandAlias

> A simple Velocity plugin to create commands to connect to a server using its name.

## Usage

Simply configure servers in your `velocity.toml` and commands will be created using the name of the server.

For instance, the following configuration will create two new commands: `/survival` and `/creative` allowing players to
connect to another server without having to type the full `/server <name>`.

```toml
[servers]
survival = "127.0.0.1:25566"
creative = "127.0.0.1:25567"
```
