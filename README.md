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
![The_Persistence_of_Memory.jpg](The_Persistence_of_Memory.jpg)<br>
https://en.wikipedia.org/wiki/File:The_Persistence_of_Memory.jpg
### 1st iteration
Luminance:<br>
![luminance_1.png](target%2Fout%2Fluminance%2Fluminance_1.png)
<br>
Gradient:<br>
![gradient_1.png](target%2Fout%2Fgradient%2Fgradient_1.png)
<br>
Path with lowest energy:<br>
![cut_path_1.png](target%2Fout%2Fcut_path%2Fcut_path_1.png)
<br>
Output:<br>
![output_1.png](target%2Fout%2Foutput%2Foutput_1.png)
### 64th iteration
Output: *notice that the relevant objects in the image were left almost unchanged*<br>
![output_64.png](target%2Fout%2Foutput%2Foutput_64.png)