package daemon;
//
// Tpj.java        TPJ Micro-services main
// ----------------------------------------------------------------------------
// History:
// --------
// 02/10/20 M. Gill	Initial creation.
// ----------------------------------------------------------------------------
//
import java.lang.*;
import com.qkernel.*;

public class Tpj
{
    public  static TpjDaemon tpj; 

    public static void main(String argvs[])
    {
	tpj = new TpjDaemon("ZeroOne");
	tpj.startDaemon(argvs);
    }
}
