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

int lkcost2[4];		/*the link cost between node 2 and other nodes*/
struct distance_table /*define distance table*/
{
  int costs[4][4];
} dt2;


extern void printdt2(struct distance_table *dtptr);
extern void linkhandler2(int linkid, int newcost);

/* students to write the following two routines, and maybe some others */
void rtinit2()
{

}


void rtupdate2(rcvdpkt)
  struct rtpkt *rcvdpkt;
{

}

void printdt2(dtptr)
  struct distance_table *dtptr;  
{
  printf("                via     \n");
  printf("   D2 |    0     1    3 \n");
  printf("  ----|-----------------\n");
  printf("     0|  %3d   %3d   %3d\n",dtptr->costs[0][0], dtptr->costs[0][1],dtptr->costs[0][3]);
  printf("dest 1|  %3d   %3d   %3d\n",dtptr->costs[1][0], dtptr->costs[1][1],dtptr->costs[1][3]);
  printf("     3|  %3d   %3d   %3d\n",dtptr->costs[3][0], dtptr->costs[3][1],dtptr->costs[3][3]);

}


/* called when cost from 2 to linkid changes from current value to newcost*/
void linkhandler2(linkid, newcost)   
  int linkid, newcost;
{

}

