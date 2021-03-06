
public class Sucin extends Polynom {
	
	private Polynom p1;
	private Polynom p2;

	public Sucin(Polynom a, Polynom b){
		p1 = a;
		p2 = b;
	}
	
	@Override
	public String toString() {
		return "(" + p1 + "*" + p2 + ")" ;
	}

	@Override
	Double valueAt(String[] vars, double[] values) {
		// TODO Auto-generated method stub
		return p1.valueAt(vars, values) * p2.valueAt(vars, values);
	}

	@Override
	Polynom derive(String var) {
		// TODO Auto-generated method stub
		return new Sucet (new Sucin(p1.derive(var),p2),new Sucin(p2.derive(var),p1));
	}

	@Override
	Polynom simplify() {
		p1 = p1.simplify();
		p2 = p2.simplify();
		
		if(p1 instanceof Konstanta && p2 instanceof Konstanta)
		{
			return new Konstanta(((Konstanta)p1).getKonstanta() * ((Konstanta)p2).getKonstanta());
		}
		if(p1 instanceof Konstanta && ((Konstanta)p1).getKonstanta() == 1.0)
		{
			return p2;
		}
		if(p2 instanceof Konstanta && ((Konstanta)p2).getKonstanta() == 1.0)
		{
			return p1;
		}	
		
		return this;
	}
}
