# chorescomplete
Children's chore tracking application.  Allows child to check off completed chores while ensuring they receive payment for work completed.

So, I am pretty excited about an improvement made recently.  It's by no means breathtaking, but the underlying reasoning excites me.  This
application uses a polling approach to handling GUI events (don't say it I know...).  Each thread spins (polls) waiting to handle various events.  Ultimately, 
this simple application began to overheat my computer.  Nothing dire, just concering.  This led me to viewing its resource consumption
and I discovered it was using 200% CPU (activity monitor).  

I assumed my OS would manage the jobs and handle this multithreaded application without issue.  I was mistaken. After further 
consideration it started to make an awful lot of sense.  Without many other processes competing against this application (user space),
it was free to dominate compute cycles continuously.  I would have never assumed the job scheduler would allow this to happen.  From
the outset, I wouldn't have even thought this to be an issue.  Well, my initial thoughts on fixing this led to a few different solutions.
Switch to some sort of interrupt event handling scheme or force the scheduler to switch out my process and break the continuous compute
cycles.  The first being a little more ambitious.  I choose to use some OS theory knowledge and force the threads to sleep regularly in
hopes that the scheduler would be able to manage the threads more efficiently.  This small simple fix, a call to sleep() at the end of
each loop, entirely fixed the problem.

I figure no one will read this and did not want to celebrate this in some self important manner.  If one person can find this and gain
something from it I would be honored.  :-D
