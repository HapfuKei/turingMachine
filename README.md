# 🤖 Turing Machine Simulator

## Table of Contents
- [Quick Start](#-quick-start)
- [Introduction](#-introduction)
- [Interface](#-interface)
- [Usage](#-usage)
- [Example Code Rules](#-example-code-rules)
- [Conclusion](#-conclusion)
- [Contributing](#-contributing)
- [License](#-license)

## 🚀 Quick Start

### Prerequisites
- Docker
- Git

### Installation and Running

1. **Clone and Navigate:**
   ```shell
   cd ~/turing/turingMachine
   git pull --rebase --autostash
   
2. **Build:**
    ```shell
    ./gradlew -Pvaadin.productionMode=true bootJar
3. **Manage Docker Containers:**
Remove existing containers and images:
  ```shell
  docker-compose down --rmi all
Deploy containers in detached mode:
  ```shell
  docker-compose up
4. **Enter**
localhost:8010

## 📚 Introduction

The Turing Machine Simulator provides a platform to simulate and program Turing machines. Rooted in the 1936 computational theory proposed by Alan Turing, this concept encompasses a machine working on an infinite one-dimensional tape, manipulating cells under a set of predefined rules and commands.

## 🕹️ Interface

The user interface, realized with the Vaadin framework, offers an interactive platform providing various functionalities. Operations such as loading data, resetting, and single or complete execution of machine steps are facilitated through assorted buttons and fields.

## 📖 Usage

Interact with the simulator by:

Specifying a max "Cell count" and optionally selecting a start position.
Inputting data manually or through "Input string" and "Load data" button.
Defining an initial state in "Current head state" or utilizing the default.
Composing Turing code according to provided guidelines.
Executing one step or running to completion with the respective buttons.
## 💻 Example Code Rules
q0/a= b q1 > ;

