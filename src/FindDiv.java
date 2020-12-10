import parcs.AM;
import parcs.AMInfo;
import parcs.channel;
import parcs.point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FindDiv implements AM {

    public void run(AMInfo info){
        Range range = (Range) info.parent.readObject();

        long a = range.getA();
        long b = range.getB();
        long res = 1;

        point p = null;
        channel c = null;

        long N = range.getN();

        if (range.getNext() != null)
        {
            System.out.println("NOT NULL");
            p = info.createPoint();
            c = p.createChannel();

            p.execute("FindDiv");
            c.write(range.getNext());
        }
        System.out.println("FOR");

        for (long n = a; n < b; n++)
        {
            if (ferma(n)) {
                if (res < n)
                    res = n;
            }
        }
        System.out.println("Range (" + range.getA() + ", " +  range.getB() + ")" + res);
        if (range.getNext() != null) {
            res = Math.max(c.readLong(), res);
        }
        info.parent.write(res);
        System.out.println("END");
    }

    public boolean ferma(long num)
    {
        if(num == 2)
		    return true;
        Random rand = new Random();
        
        for(int i=0;i<20;i++){
            long a = num/2-1;
            if (gcd(a, num) != 1)
                return false;			
            if( pows(a, num-1, num) != 1)		
                return false;			
        }
        return true;
    }

    public long gcd(long a, long b){
        if(b==0)
            return a;
        return gcd(b, a%b);
    }
    public long mul(long a, long b, long m){
        if(b==1)
            return a;
        if(b%2==0){
            long t = mul(a, b/2, m);
            return (2 * t) % m;
        }
        return (mul(a, b-1, m) + a) % m;
    }

    public long pows(long a, long b, long m){
        if(b==0)
            return 1;
        if(b%2==0){
            long t = pows(a, b/2, m);
            return mul(t , t, m) % m;
        }
        return ( mul(pows(a, b-1, m) , a, m)) % m;
    }
}
