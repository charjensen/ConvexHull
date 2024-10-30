# Convex Hull

Demo:

[![Convex Hull](https://img.youtube.com/vi/WBNjOkRs1NU/0.jpg)](https://www.youtube.com/watch?v=WBNjOkRs1NU)

This Java program computes the convex hull of a set of points using both brute force and QuickHull algorithms. The program uses the `DUDraw` library for visualizing the points and the convex hull.

## Features

- **Brute Force Algorithm**: Computes the convex hull using a brute force approach.
- **QuickHull Algorithm**: Computes the convex hull using the QuickHull algorithm.

## Usage

### Visualize Points and Convex Hull

1. The program initializes a set of points and displays them.
2. It computes the convex hull using the brute force algorithm and visualizes the result.
3. It then computes the convex hull using the QuickHull algorithm and visualizes the result.

## Code Overview

### Main Class: `ConvexHull`

- **Main Method**: Initializes the points, computes the convex hull using both algorithms, and visualizes the results.
- **QuickHullStart Method**: Initializes the QuickHull algorithm.
- **QuickHull Method**: Recursively computes the convex hull using the QuickHull algorithm.

### Dependencies

- **DUDraw**: Library for drawing and handling user interactions.
