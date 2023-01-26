# SeamCarving
My implementation of the Seam Carving algorithm (in Java).
Given an input image, the algorithm determines and removes the vertical pixel path in the image that has the least energy, i.e. the least change in color along the path, using dynamic programming.
With this method, the size of an image can be successively reduced by removing mostly the background of the image while leaving relevant objects almost unchanged.

This small project was inspired by the MIT lecture https://www.youtube.com/watch?v=rpB6zQNsbQU by Grant Sanderson.
For more information on the algorithm watch the lecture or see the wikipedia page https://en.wikipedia.org/wiki/Seam_carving.

[![IMAGE ALT TEXT](http://img.youtube.com/vi/rpB6zQNsbQU/0.jpg)](http://www.youtube.com/watch?v=rpB6zQNsbQU "Video Title")

# Build and Usage
1. `mvn package`
2. `cd target`
3. `java -jar SeamCarving-1.0.0.jar <path to input image> <path to output folder> <number of iterations> <store debug images> <store final result only>`
    - `path to input image`: string
    - `path to output folder`: string
    - `number of iterations`: integer (how many paths to cut; the image is resized to original_width minus iterations in pixels)
    - `store debug images`: boolean [true|false] (store images showing the luminance, gradient, path)
    - `store final result only`: boolean [true|false] (store the images of the final iteration only)

# Results
### Input
![The_Persistence_of_Memory](https://user-images.githubusercontent.com/34870366/214943065-8a2c0bdc-381c-42bd-9fd6-3a7a98547c8a.jpg)
<br>
https://en.wikipedia.org/wiki/File:The_Persistence_of_Memory.jpg
### 1st iteration
Luminance:<br>
![luminance_1](https://user-images.githubusercontent.com/34870366/214942927-c196e62a-e19f-4b96-b698-0aa85a5eaa39.png)
<br>
Gradient:<br>
![gradient_1](https://user-images.githubusercontent.com/34870366/214942885-e5c41acd-9123-4626-9a07-188525e37494.png)
<br>
Path with lowest energy:<br>
![cut_path_1](https://user-images.githubusercontent.com/34870366/214942856-3f8b9ff6-f6d9-4589-8662-8208f5803237.png)
<br>
Output:<br>
![output_1](https://user-images.githubusercontent.com/34870366/214942801-b25724ed-c50d-4e32-befd-46cc7060b339.png)
### 64th iteration
Output: *notice that the relevant objects in the image were left almost unchanged*<br>
![output_64](https://user-images.githubusercontent.com/34870366/214942829-6c05c7f3-b800-4099-a7c8-14681d7dea25.png)
