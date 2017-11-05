
import java.io.*;
import java.util.*;

public class Main {

	Scanner in;
	PrintWriter out;

	Main() throws IOException {
		// in = new InputReader(System.in);
		// in = new Scanner(new File("SiouxFalls.txt"));
		in = new Scanner(new File("EasternMass.txt"));
		out = new PrintWriter(System.out);

		// out = new PrintWriter(new File("output_.txt"));
		solve();
		out.close();
	}

	public static void main(String args[]) {
		new Thread(null, new Runnable() {
			public void run() {
				try {
					new Main();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, "1", 1 << 26).start();
	}

	// --------------------My Code Starts Here----------------------
	long mod = (long) 1e9 + 7;
	double eps = 1e-5;
	int len = 7;

	public void solve() throws IOException {
		int n = Integer.parseInt(in.nextLine().trim());
		List<Integer>[] graph = new List[n + 1];
		for (int i = 0; i <= n; i++) {
			graph[i] = new ArrayList<>();
		}
		while (in.hasNextLine()) {
			String s[] = in.nextLine().trim().split("\t");
			// debug(s);
			graph[Integer.parseInt(s[0])].add(Integer.parseInt(s[1]));
		}
		BiconnectedComponents bc = new BiconnectedComponents();
		List<List<Integer>> components = bc.biconnectedComponents(graph);

		System.out.println("biconnected components:" + components);
		System.out.println("cutPoints: " + bc.cutPoints);
		System.out.println("bridges:" + bc.bridges);

		long majorContribution = MajorContribution(graph);
		System.out.println("Major Contribution = " + majorContribution);

		ArrayList<Pair> listOfRoads = getNewRoadPriority(graph, n);
		for (Pair p : listOfRoads) {
			System.out.println(p.s + " " + p.val);
		}
	}

	// ---------------------My Code Ends Here----------------------
	long MajorContribution(List<Integer>[] graph) {
		BiconnectedComponents bc = new BiconnectedComponents();
		List<List<Integer>> components = bc.biconnectedComponents(graph);

		// System.out.println("biconnected components:" + components);
		// System.out.println("cutPoints: " + bc.cutPoints);
		// System.out.println("bridges:" + bc.bridges);

		long value = 0;
		long sum = 0;
		for (List<Integer> list : components) {
			sum += list.size();
		}

		for (List<Integer> list : components) {
			value += (sum - list.size()) * (list.size());
		}

		value /= 2;

		return value;
	}

	ArrayList<Pair> getNewRoadPriority(List<Integer>[] graph, int n) {
		ArrayList<Pair> list = new ArrayList<>();
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (i == j) {
					continue;
				}
				graph[i].add(j);
				long value = MajorContribution(graph);
				graph[i].remove(graph[i].size() - 1);

				String s = "(" + i + "," + j + ")";
				list.add(new Pair(s, value));
			}
		}
		Collections.sort(list);
		return list;
	}

	class Pair implements Comparable<Pair> {
		String s;
		long val;

		Pair(String ss, long sval) {
			s = ss;
			val = sval;
		}

		@Override
		public int compareTo(Pair o) {
			return Long.compare(this.val, o.val);
		}
	}

	public class BiconnectedComponents {

		List<Integer>[] graph;
		boolean[] visited;
		Stack<Integer> stack;
		int time;
		int[] tin;
		int[] lowlink;
		List<List<Integer>> components;
		List<Integer> cutPoints;
		List<String> bridges;

		public List<List<Integer>> biconnectedComponents(List<Integer>[] graph) {
			int n = graph.length;
			this.graph = graph;
			visited = new boolean[n];
			stack = new Stack<>();
			time = 0;
			tin = new int[n];
			lowlink = new int[n];
			components = new ArrayList<>();
			cutPoints = new ArrayList<>();
			bridges = new ArrayList<>();

			for (int u = 0; u < n; u++)
				if (!visited[u])
					dfs(u, -1);

			return components;
		}

		void dfs(int u, int p) {
			visited[u] = true;
			lowlink[u] = tin[u] = time++;
			stack.add(u);
			int children = 0;
			boolean cutPoint = false;
			for (int v : graph[u]) {
				if (v == p)
					continue;
				if (visited[v]) {
					// lowlink[u] = Math.min(lowlink[u], lowlink[v]);
					lowlink[u] = Math.min(lowlink[u], tin[v]);
				} else {
					dfs(v, u);
					lowlink[u] = Math.min(lowlink[u], lowlink[v]);
					cutPoint |= lowlink[v] >= tin[u];
					// if (lowlink[v] == tin[v])
					if (lowlink[v] > tin[u])
						bridges.add("(" + u + "," + v + ")");
					++children;
				}
			}
			if (p == -1)
				cutPoint = children >= 2;
			if (cutPoint)
				cutPoints.add(u);
			if (lowlink[u] == tin[u]) {
				List<Integer> component = new ArrayList<>();
				while (true) {
					int x = stack.pop();
					component.add(x);
					if (x == u)
						break;
				}
				components.add(component);
			}
		}

	}

	public static void debug(Object... o) {
		System.out.println(Arrays.deepToString(o));
	}

}
