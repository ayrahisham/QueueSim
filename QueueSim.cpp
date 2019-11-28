/*Nur Suhaira (5841549)
Queues are commonly used in network systems. 
For example, e-mail is placed in queues while it is waiting to be sent 
and after it arrives at the recipient’s mailbox. A problem occurs, 
however, if the outgoing mail processor cannot send one or 
more of the messages in the queue. For example, a message 
might not be sent because the recipient’s system is no available.   

Write an e-mail simulator that processes mail at an average 
of 40 messages per minute. As messages are received, they are 
placed in a queue. For the simulation, assume that the messages 
arrive at an average rate of 30 messages per minute. 
Remember, the messages must arrive randomly, 
so you will need to use a random number generator 
to determine when messages are received.   

Each minute, you can dequeue up to 40 messages and send them. 
Assume that 25% of the messages in the queue cannot be sent in 
any processing cycle. 

Again, you will need to use a random number to determine 
whether a given message can be sent. If it cannot be sent, 
put it back at the end of the queue or enqueue it.   
Run the simulator for 10 to 15 minutes, tracking the number of 
times each message had to be requeued. At the end of the simulation, 
print the statistics that show:  

1 The total messages processed  
2 The average arrival rate, that is, the average number of messages 
arriving per minute.  
3 The average number of messages sent per minute. 
4 The average number of messages in the queue in a minute. 
5 The number of messages sent on the first attempt, 
the number sent on the second attempt, and so forth.  
6 The average number of times messages had to be requeued 
(do not include the messages sent the first time in this average.)   
*/

#include <iostream>
#include <cstdlib>
#include <ctime>
using namespace std;

const int SIZE = 1000;
const int SECPERMIN = 60;

class EmailQueue
{
	public:
		
		//Default constructor
		EmailQueue ();
		
		//Destructor
		~EmailQueue ();
		
		//Copy constructor
		EmailQueue (const EmailQueue&);
		
		//Enqueue function
		void enqueue (int numofMails);
		
		//Dequeue function
		void dequeue ();
		
		//isEmpty function
		bool isEmpty () const;
		
		//printQueue function
		void printQueue () const;
		
		void setHead (int num);
		
		void setTail (int num);
		
	private:	
		int queue [SIZE];
		int head, tail;
};

int main ()
{
	srand (time (NULL));
	
	int userMinute = 0;
	int numOfMin = 1;
	static int numberOfRequeue = 0;
	
	int totalNumOfEmailProcessed = 0;
	int numOfEmailProcess = 0;

	int numOfMinute = 0;
	
	int totalNumOfEmailArrive = 0;
	int numOfEmailArrive = 0;
	
	int numOfEmailSent = 0;
	static int totalNumOfEmailInQueue = 0;
	
	EmailQueue eq;
	
	int time = 1;
	int endTime = 0; // Simulate num of seconds based on user's input (in minutes)
	int tick = 1; // Each tick = 1 second

	do
	{
		cout << "Enter a number of minutes for simulation [10 - 15]: ";
		cin >> userMinute;
	
		if ((userMinute < 10) || (userMinute > 15))
		{
			cout << endl
				 << "Number of minutes for simulation should be between 10 and 15!" 
				 << endl << endl;
		}
	} while ((userMinute < 10) || (userMinute > 15));
	
	endTime = userMinute * SECPERMIN;
	
	while (time <= endTime)
	{
		if (time % SECPERMIN == 0)
		{	 
			numOfEmailArrive = rand () % 100 + 1;
			totalNumOfEmailInQueue += numOfEmailArrive;
			totalNumOfEmailArrive += numOfEmailArrive;
			
			eq.enqueue (numOfEmailArrive);
			
			cout << endl
				 << "Email IDs in Queue after enqueue: " << endl;
				
			if (!eq.isEmpty())
			{
				eq.printQueue ();
			}
				
			numOfEmailProcess = (75 / 100.0) * numOfEmailArrive;
			
			if (numOfEmailProcess <= 40)
			{
				for (int i = 0; i < numOfEmailProcess; i++)
				{
					eq.dequeue ();
				}
					
				numOfEmailSent = numOfEmailProcess;
				totalNumOfEmailInQueue -= numOfEmailSent;
				totalNumOfEmailProcessed += numOfEmailSent;
			}
			else
			{
				for (int i = 0; i < 40; i++)
				{
					eq.dequeue ();
				}
					
				numOfEmailSent = 40;
				totalNumOfEmailInQueue -= numOfEmailSent;
				totalNumOfEmailInQueue += (numOfEmailProcess - numOfEmailSent);
				++numberOfRequeue;
				totalNumOfEmailProcessed += numOfEmailSent;
			}
				
			cout << endl
				 << "Email IDs in Queue after dequeue: " << endl;
					 
			if (!eq.isEmpty())
			{
				eq.printQueue ();
			}
				
			cout << endl
				 << "At the end of " << numOfMin << " minute simulation:\n"
				 << "\t1 The total messages processed: " << totalNumOfEmailProcessed << endl
				 << "\t2 The average arrival rate: " << totalNumOfEmailArrive/numOfMin << endl
				 << "\t3 The average number of messages sent per minute: " << totalNumOfEmailProcessed/numOfMin << endl
				 << "\t4 The average number of messages in the queue in a minute: " << totalNumOfEmailInQueue/numOfMin << endl
				 << "\t5 The number of messages sent on " << time/60 << " attempt(s): " << numOfEmailSent << endl;
			
			if (numOfMin > 1)
			{
				cout << "\t6 The average number of times messages had to be requeued: " << numberOfRequeue/numOfMin << endl;
			}
				
			cout << endl
				 << "===============================================================================" << endl
				 << endl;
				
			eq.setHead (0);
			eq.setTail(0);
			numOfMin++;
		}
	
		time += tick;	 	 	 	
	}
}

EmailQueue::EmailQueue ()
{
	head = 0;
	tail = 0;
}

EmailQueue::~EmailQueue ()
{
	
}

EmailQueue::EmailQueue (const EmailQueue& eq)
{

}

void EmailQueue::enqueue (int numofMails)
{
	for (int i = 0; i < numofMails; i++)
	{
		queue[i] = i+1;
	}
	
	tail = numofMails;
}

void EmailQueue::dequeue ()
{
	//Queue is not empty
	if (head != tail)
	{
		head = (head + 1) % SIZE;
	}
}

bool EmailQueue::isEmpty () const
{
	if (head == 0 && tail == 0)
	{
		return true;
	}
	
	return false;
}

void EmailQueue::printQueue () const
{
	cout << endl;
	
	for (int i = head; i != tail; i = (i+1) % SIZE)
	{
		cout << queue[i] << " ";
	}
	
	cout << endl;
}

void EmailQueue::setHead (int num)
{
	head = num;
}

void EmailQueue::setTail (int num)
{
	tail = num;
}

