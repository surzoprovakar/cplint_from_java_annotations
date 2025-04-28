# 🎲 Automatic Generation of Probabilistic Logic Programs from Java Annotations

This project implements a **framework for automatically generating probabilistic logic programs** based on annotated Java code, enabling **seamless integration of probability reasoning** into Java applications.

## Overview

Probabilistic logic programming provides powerful modeling for uncertain domains, but it traditionally requires specialized languages like **Prolog**.

This project bridges the gap by:
- Allowing developers to **use simple Java annotations** to express probabilistic knowledge.
- **Automatically generating cplint-compatible Prolog programs** from Java source code.
- **Integrating probability calculations** directly into Java-based systems.

## Key Features

- ✍️ **Annotation-Based Specification**: Define probabilistic facts and rules with standard Java annotations.
- 🔄 **Automatic Translation**: Java source is parsed to generate corresponding probabilistic Prolog code.
- 🎯 **Integration with cplint**: Supports probabilistic inference using the cplint framework.
- ⚙️ **Cross-Language Workflow**: Bridges Java, C++, and Prolog for full pipeline execution.

## Technologies Used

- **Java** — Annotation processing and core framework.
- **Prolog (cplint)** — Probabilistic logic engine.
- **C++** — Support utilities and performance optimization.
- **cplint** — Probabilistic reasoning library for Prolog.

## Motivation

Traditional probabilistic programming tools require developers to learn new paradigms.  
This project lowers the entry barrier by:
- Embedding probabilistic modeling **directly into familiar Java code**,
- **Automating the generation** of the required Prolog representations,
- Allowing probabilistic analysis without extensive manual translation.

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/surzoprovakar/cplint_from_java_annotations.git
   cd cplint_from_java_annotations
