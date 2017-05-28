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

int lkcost1[4];		/*the link cost between node 1 and other nodes*/
struct distance_table /*define distance table*/
{
  int costs[4][4];
} dt1;


extern void printdt1(struct distance_table *dtptr);
extern void linkhandler1(int linkid, int newcost);

/* students to write the following two routines, and maybe some others */
void rtinit1() 
{

}


void rtupdate1(rcvdpkt)
  struct rtpkt *rcvdpkt;
{

}

void printdt1(dtptr)
  struct distance_table *dtptr;
{
  printf("                via     \n");
  printf("   D1 |    0     2    3 \n");
  printf("  ----|-----------------\n");
  printf("     0|  %3d   %3d   %3d\n",dtptr->costs[0][0], dtptr->costs[0][2],dtptr->costs[0][3]);
  printf("dest 2|  %3d   %3d   %3d\n",dtptr->costs[2][0], dtptr->costs[2][2],dtptr->costs[2][3]);
  printf("     3|  %3d   %3d   %3d\n",dtptr->costs[3][0], dtptr->costs[3][2],dtptr->costs[3][3]);

}


/* called when cost from 1 to linkid changes from current value to newcost*/
void linkhandler1(linkid, newcost)   
  int linkid, newcost;
{

}


