# Compression-of-Symbolic-Execution-Trees

This project extends the existing SET tool by introducing a top-down pushing algorithm to optimize the SET structure. The goal is to create compact, efficient, and potentially canonical versions of the original SETs, reducing the computational overhead and improving the readability and analysis of the program's execution paths.

Key Enhancements
Top-Down Pushing Algorithm: Inspired by techniques used in BDDs, this algorithm traverses the SET from the top (root) downwards, applying transformations and optimizations at each step.
Simplified Look-up Table (LT) Management: The algorithm adds declared variables to the LT and dynamically replaces variables in the node statement, streamlining the variable handling process.
Efficient Node Processing: When encountering a "return node," the algorithm terminates the current branch's processing, avoiding unnecessary computations.
Optimized Edge Handling: The tool will simplify true and false edges, potentially eliminating the need to store expensive edge values.
Input and Variable Optimization: The algorithm focuses on optimizing the handling of input variables and eliminating unnecessary non-input variables, enhancing the SET's efficiency.
Expected Outcomes
Compact SETs: The new SETs will be more compact compared to the original versions, facilitating easier analysis and visualization.
Improved Performance: By eliminating redundant data and computations, the tool will perform more efficiently, especially for complex programs.
Canonical Forms: The optimization might result in SETs that are canonical, meaning that they represent the most simplified and standardized form of the execution paths.
Enhanced Usability: The tool will become more user-friendly for program analysis, debugging, and educational purposes, due to its enhanced efficiency and clarity.
Applications
This optimized SET tool is particularly beneficial for:

Advanced Program Analysis: Handling more complex programs with a higher degree of efficiency.
Resource-Constrained Environments: Operating in environments where computational resources are limited.
Educational Demonstrations: Teaching advanced concepts in symbolic execution and optimization algorithms.
Getting Started
To use the enhanced tool, users can follow the same steps as the original SET tool, with the added advantage of the new optimization algorithm automatically applying during the SET generation and evaluation processes.
