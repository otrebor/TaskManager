# TaskManager

With Task Manager we refer to a software component that is designed for handling multiple processes inside an operating
system. Each process is identified by 2 fields, a unique unmodifiable identifier (PID), and a priority (low, medium,
high). The process is immutable, it is generated with a priority and will die with this priority – each process has a
kill() method that will destroy it

We want the Task Manager to expose the following functionality:

- Add a process
- List running processes
- Kill/KillGroup/KillAll

## Add a process

### Maximum Capacity (1/3)

The task manager should have a prefixed maximum capacity, so it can not have more than a certain number of running
processes within itself. This value is defined at build time. The add(process) method in TM is used for it. The default
behaviour is that we can accept new processes till when there is capacity inside the Task Manager, otherwise we won’t
accept any new process

### FIFO approach (2/3)

A different customer wants a different behaviour:
he’s asking to accept all new processes through the add() method, killing and removing from the TM list the oldest one (
First-In, First-Out) when the max size is reached

### Priority based (3/3)

A new customer is asking something different again, every call to the add() method, when the max size is reached, should
result into an evaluation: if the new process passed in the add() call has a higher priority compared to any of the
existing one, we remove the lowest priority that is the oldest, otherwise we skip it

## List running processes

The task manager offers the possibility to list() all the running processes, sorting them by time of creation
(implicitly we can consider it the time in which has been added to the TM), priority or id.

## Kill, KillGroup and KillAll

Model one or more methods capable of

1. killing a specific process
2. killing all processes with a specific priority
3. killing all running processes

## Developer notes

- The goal is to obtain code that is a trade off between algorithmic efficiency (Optimal in Big O), simplicity,
  extendability and separation of concerns.
- Time constraints didn't allow me to test every line of the code
- Time constraints didn't allow me to use configuration files to select what task manager to use
