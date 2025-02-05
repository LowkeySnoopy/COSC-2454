public class Centipede {
    Segment head;


    int dx, dy;

    int horizontalDir;  //this indicates if we are moving left or right

    int verticalMoveCount = 0;  //tracks how far we've moved down during

    int verticalLim = 20;  //how many pixels to move down each time we switch

    public Centipede(int startX, int startY, int length) {
        this.dx = 7; // centides xspeed
        this.dy = 0; //centipede yspeed
        this.horizontalDir = 1; //+1 for right, -1 for left

        //Building the linked segements
        head = new Segment(startX, startY);
        Segment current = head;

        for (int i = 1; i < length; i++) {
            current.next = new Segment(startX - (i * 15), startY);
            current = current.next;
        }
    }

        public void moved(){
            if (head == null) return;

            //Save old head position
            int prevX = head.x;
            int prevY = head.y;

            //Move head
            head.x += dx;
            head.y += dy;

            //Move body
            Segment current= head.next;
            while(current != null){
                int tempX = current.x;
                int tempY = current.y;
                current.x = prevX;
                current.y = prevY;
                prevX = tempX;
                prevY = tempY;
                current = current.next;

            }

        }
    public void checkAndTurn(int panelWidth) {
        if (head == null) return;

        //the centipede's approx. width
        int segSize = 15;
        int rBound = panelWidth - segSize;
        int lBound = 0;


        //1. we are moving horizontally (dy==0)
        if (dy == 0){
            if (horizontalDir == 1 && head.x >= rBound) {
                dx = 0;
                dy = 7;
                verticalMoveCount = 0;
            } else if (horizontalDir == -1 && head.x <= lBound) {
                dx = 0;
                dy = 7;
                verticalMoveCount = 0;
            } else {
                verticalMoveCount += Math.abs(dy);

                if (verticalMoveCount >= verticalLim) {

                    horizontalDir = -horizontalDir;
                    dx=7*horizontalDir;
                    dy=0;




                }
            }
        }
    }
    public void split(Segment splitPoint){
        if(splitPoint == head) {
            head = null;
        }else{
            Segment current =head;
            while (current!= null && current.next != splitPoint){
                current= current.next;
            }
            if (current != null){
                current.next = null;

            }
        }
    }
}

