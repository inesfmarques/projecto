// Implements left and right-side matroids of a bipartite graph
// An edge from v to w is represented by int[] {v, w}
// This implementation has a worse performance than BipGMatroid

package bgraph;

import java.util.ArrayList;

import matroid.Matroid;

public class BipGMatroidBad implements Matroid<int[], ArrayList<int[]>> {
	
	BipG G; // Graph associated to the matroid
	boolean side; // true if it's the left-side matroid, false if it's the right-side matroid
	
	// Constructor
	public BipGMatroidBad(BipG G, boolean side) {
		this.G = G;
		this.side = side;
	}

	// Returns a list with the elements in the matroid ground set
	public ArrayList<int[]> getGroundSet() {
		// The ground set is the set of edges
		
		ArrayList<int[]> list = new ArrayList<int[]>();
		ArrayList<Integer> lovers;
		for (int i = 0; i < G.getL() + G.getR(); i++) {
			lovers = G.lovers(i);
			for (int j = 0; j < lovers.size(); j++) list.add(new int[] {i, lovers.get(j)});
		}

		return list;
	}

	// Input:
	// --> set: ArrayList of elements Type
	// Output: 'true' if set is in the matroid, 'false' otherwise
	public boolean belongsTo(ArrayList<int[]> set) {
		// Check if any two edges have the same ending on the left or right side
		
		int L = G.getL(), R = G.getR(), v;
		
		// Different cases according to 'side'
		if (side) {
			// The i-th position of 'endings' is true if we've seen any edge with an endpoint on i
			boolean[] endings = new boolean[L];
			for (int i = 0; i < set.size(); i++) {
				if (set.get(i)[0] < L) v = set.get(i)[0];
				else v = set.get(i)[1];
				
				if (endings[v]) return false;
				else endings[v] = true;
			}
			
			return true;
		}
		
		else {
			// The i-th position of 'endings' is true if we've seen any edge with an endpoint on (i+L)
			boolean[] endings = new boolean[R];
			for (int i = 0; i < set.size(); i++) {
				if (set.get(i)[0] < L) v = set.get(i)[1] - L;
				else v = set.get(i)[0] - L;
				
				if (endings[v]) return false;
				else endings[v] = true;
			}
			
			return true;
		}
	}
	
	// Converts an ArrayList into a Subset, to be used with the remaining methods
	public ArrayList<int[]> arrayToSubset(ArrayList<int[]> set){
		return set;
	}

	// Input:
	// --> set: ArrayList of elements of the ground set
	// --> x: element not in set
	// Output: 'true' if (set U {x}) is in the matorid, 'false' otherwise
	public boolean belongsTo(ArrayList<int[]> set, int[] x) {
		// Check if any edge in set has the same ending as x
		
		int L = G.getL(), v;
		
		// Different cases according to 'side'
		if (side) {
			if (x[0] < L) v = x[0];
			else v = x[1];
			
			for (int i = 0; i < set.size(); i++) {
				if (set.get(i)[0] < L && set.get(i)[0] == v) return false;
				if (set.get(i)[1] < L && set.get(i)[1] == v) return false;
			}
			
			return true;
		}
		
		else {
			if (x[0] < L) v = x[1];
			else v = x[0];
			
			for (int i = 0; i < set.size(); i++) {
				if (set.get(i)[0] < L && set.get(i)[1] == v) return false;
				if (set.get(i)[1] < L && set.get(i)[0] == v) return false;
			}
			
			return true;
		}
	}

	// Input:
	// --> set: ArrayList of elements of the ground set
	// --> x: element not in set
	// --> y: element in set
	// Output: 'true' if (set U {x}) \ {y} is in the matorid, 'false' otherwise
	public boolean belongsTo(ArrayList<int[]> set, int[] x, int[] y) {
		// Check if any edge in (set \ {y}) has the same ending as x

		int L = G.getL(), v;
		
		// Different cases according to 'side'
		if (side) {
			if (x[0] < L) v = x[0];
			else v = x[1];
			
			for (int i = 0; i < set.size(); i++) {
				if (set.get(i)[0] < L && set.get(i)[0] == v) {
					// If set.get(i) is y, then the output is true. Otherwise, it's false
					if (set.get(i)[0] == y[0] && set.get(i)[1] == y[1]) return true;
					else return false;
				}
				if (set.get(i)[1] < L && set.get(i)[1] == v) {
					// If set.get(i) is y, then the output is true. Otherwise, it's false
					if (set.get(i)[0] == y[0] && set.get(i)[1] == y[1]) return true;
					else return false;
				}
			}
			
			return true;
		}
		
		else {
			if (x[0] < L) v = x[1];
			else v = x[0];
			
			for (int i = 0; i < set.size(); i++) {
				if (set.get(i)[0] < L && set.get(i)[1] == v) {
					// If set.get(i) is y, then the output is true. Otherwise, it's false
					if (set.get(i)[0] == y[0] && set.get(i)[1] == y[1]) return true;
					else return false;
				}
				if (set.get(i)[1] < L && set.get(i)[0] == v) {
					// If set.get(i) is y, then the output is true. Otherwise, it's false
					if (set.get(i)[0] == y[0] && set.get(i)[1] == y[1]) return true;
					else return false;
				}
			}
			
			return true;
		}
	}

}
