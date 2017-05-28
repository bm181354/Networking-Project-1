#include <stdio.h>
#define INFINITY 9999

typedef int bool;
#define true 1
#define false 0

struct rtpkt {
  int sourceid;       /* id of sending router sending this pkt */
  int destid;         /* id of router to which pkt being sent 
                         (must be an immediate neighbor) */
  int mincost[4];    /* min cost to node 0 ... 3 */
};

extern void tolayer2(struct rtpkt packet);

extern int TRACE;
extern double clocktime;

int lkcost0[4];				/*The link cost between node 0 and other nodes*/
struct distance_table 		/*Define distance table*/
{
  int costs[4][4];
} dt0;


extern void printdt0(struct distance_table *dtptr);
extern void linkhandler0(int linkid, int newcost);

/* students to write the following two routines, and maybe some others */
void rtinit0() 
{

}


void rtupdate0(rcvdpkt)
  struct rtpkt *rcvdpkt;
{

}

void printdt0(dtptr)
  struct distance_table *dtptr;
{
  printf("                via     \n");
  printf("   D0 |    1     2 \n");
  printf("  ----|-----------------\n");
  printf("     1|  %3d   %3d \n",dtptr->costs[1][1], dtptr->costs[1][2]);
  printf("dest 2|  %3d   %3d \n",dtptr->costs[2][1], dtptr->costs[2][2]);
  printf("     3|  %3d   %3d \n",dtptr->costs[3][1], dtptr->costs[3][2]);
}


/* called when cost from 0 to linkid changes from current value to newcost*/
void linkhandler0(linkid, newcost)   
  int linkid, newcost;
{

}



