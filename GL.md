#VAO VBO
####Vertex Array Object and Vertex Buffer Object
VAO has multiple slots called attributes
There is 15 attributes like location, color etc...
VBO is just data and can be anything like position, color etc...
Each VAO has unique ID in memory
The 2D/3D objects are arrays of vertices
The objetc are group of triangles (3 vertices, 3 points in 2 or 3 dimensional space)
So each vertex has 2 or 3 coordinates (x, y, z)
So the single triangle vertex coords are:
(x1, y1, z1), (x2, y2, z2), (x3, y3, z3)
Then we can put list of this triangles with appropriate coors in to VBO
Then we can put that VBO as an attribute in VAO, get the ID and give it to OpenGL to render
####VAO optimisation using indices
given we want to render shape with following vertices (remember the vertices should construct triangles)

~~~
float[] vertices = { 
    -0.5f, 0.5f, 0f, 
    -0.5f, -0.5f, 0f, 
    0.5f, -0.5f, 
    0f, 0.5f, -0.5f, 
    0f, 0.5f, 0.5f, 
    0f, -0.5f, 0.5f, 0f
};
~~~

we will notice that some vertices are duplicated.
So we can remove some of them.
~~~
final float[] vertices = {
    -0.5f, 0.5f, 0f,
    -0.5f, -0.5f, 0f,
    0.5f, -0.5f, 0f,
    0.5f, 0.5f, 0f,
};
~~~
And use indexing instead, like follows
~~~
final int[] indices = {
    0, 1, 3,
    3, 1, 2
};
~~~
Tis means render 0nd vertices `(-0.5f, 0.5f, 0f)` then 1nd `(-0.5f, -0.5f, 0f)` and so on...

#Shaders
There is a good video explaining shaders https://www.youtube.com/watch?v=V0E5WH7JRLo

#Textures
For the textures use this library http://www.slick.ninjacave.com/
Note the texture coordinates are not same as OpenGL coordinates (the top left corner is `(0, 0)`)

#Matrices & Uniform Variables
For solving the problem of passing the new/updated VAO data to the shader model, we using uniform variables

#Entity
Entity has a model, position vector, rotation and scale.
All of this constructs transformation or uniform matrix.
There is also projection matrix which has field of view angle, near plane distance and also the far plane distance.

#Camera
The camera has position, pitch, yaw and roll. In reality the camera is not moving (the whole world moving across him).