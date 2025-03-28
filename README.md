# Multi threading and multi process projects


This project is divided into two projects. The first one is to create multiple processes that can communicate with each other using MapperByteBuffer. The communication is handled by a RandomAccessFile in Java. When one program sends a message, the other running processes receive the message. 


The second one involves creating a multithreading program to control a Lego EV3 robot. The idea is to have a robot executing geometric shapes using a non-blocking user interface. Every time a geometrical form is required, a thread is created. Since the user interface is non-blocking, multiple commands can be sent at the same time. To solve this issue, semaphores are used, where the first thread acquires the resources and the subsequent threads must wait until the one with the resources releases them.


Example of the ui from multi process program:

<img src=".//markdown_images/ui_multi_process.png" alt="drawing" width="500"/>


Example of the ui and UML from multi thread program:

<img src=".//markdown_images/ui_multi_thread.png" alt="drawing" width="500"/>
<img src=".//markdown_images/uml.png" alt="drawing" width="500"/>