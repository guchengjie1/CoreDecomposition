**Algorithm:**CoreDecomposition

***

**input**:G,non-negative k

**output**:Gs

***

1  find the vertices in G whose degrees are less than k ,  put them into queue q;

2  while (q is not empty) do

3  	u  = q.gettop();

4  	update the degree of each neighbor v of node u;

5  	if (degree of v is less than k) then

6  		q.put(v);

7  	Gs = update G

3  return Gs

***

