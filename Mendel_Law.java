/* Samantha Sturges
 * Bioinformatics Algorithms
 * All programs are calculated using problems from rosalind.info
 * This problem is located at http://rosalind.info/problems/iprb/
 *
 * This program builds a tree based on pre-determined information:
 * 1- The array order of the genotypes are always homozygous dominant(AA), heterozygous(Aa), and homozygous recessive(aa).
 * 2- The dominant probability is based off calculations of a punnett square- results have been determined based on the position in the array
 */

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Mendel_Law
{
	Node root = new Node(null);
	double[] genotypes = new double[3];

	public class Node
	{
		public Node parent_node;
		public ArrayList<Node> child_node = new ArrayList<Node>();
		//event_prob defines the probability of having that genotype
		//dom_prob defines the probability that the genotype will have a dominant allele
		public double event_prob, dom_prob;

		//Defines each node of the tree
		public Node(Node parent)
		{
			if (parent != null)
			{
				this.parent_node = parent;
				parent.child_node.add(this);

				//Defines the dom_prob as -1 (if the result is negative, the calculations are incorrect) or using pre-determined information.
				//Sets the event_prob based on the parent nodes and array index
				if (parent == root)
				{
					dom_prob = -1;
					event_prob = (genotypes[parent.child_node.indexOf(this)] + 0.0)/ (genotypes[0] + genotypes[1] + genotypes[2] + 0.0);
				}
				else
				{
					if (parent.parent_node.child_node.indexOf(parent) == 0)
					{
						dom_prob = 1;
						if (parent.child_node.indexOf(this) == 0)
						{
							event_prob = (genotypes[0] - 1.0)/ (genotypes[0] + genotypes[1] + genotypes[2] - 1.0);
						}
						else if (parent.child_node.indexOf(this) == 1)
						{
							event_prob = (genotypes[1] - 0.0)/ (genotypes[0] + genotypes[1] + genotypes[2] - 1.0);
						}
						else
						{
							event_prob = (genotypes[2] - 0.0)/ (genotypes[0] + genotypes[1] + genotypes[2] - 1.0);
						}
					}
					else if (parent.parent_node.child_node.indexOf(parent) == 1)
					{
						if (parent.child_node.indexOf(this) == 0)
						{
							dom_prob = 1;
							event_prob = (genotypes[0] - 0.0)/ (genotypes[0] + genotypes[1] + genotypes[2] - 1.0);
						}
						else if (parent.child_node.indexOf(this) == 1)
						{
							dom_prob = 0.75;
							event_prob = (genotypes[1] - 1.0)/ (genotypes[0] + genotypes[1] + genotypes[2] - 1.0);
						}
						else
						{
							dom_prob = 0.50;
							event_prob = (genotypes[2] - 0.0)/ (genotypes[0] + genotypes[1] + genotypes[2] - 1.0);
						}
					}
					else
					{
						if (parent.child_node.indexOf(this) == 0)
						{
							dom_prob = 1;
							event_prob = (genotypes[0] - 0.0)/ (genotypes[0] + genotypes[1] + genotypes[2] - 1.0);
						}
						else if (parent.child_node.indexOf(this) == 1)
						{
							dom_prob = 0.50;
							event_prob = (genotypes[1] - 0.0)/ (genotypes[0] + genotypes[1] + genotypes[2] - 1.0);
						}
						else
						{
							dom_prob = 0.0;
							event_prob = (genotypes[2] - 1.0)/ (genotypes[0] + genotypes[1] + genotypes[2] - 1.0);
						}
					}
				}
			}

			return;
		}
	}

	public Mendel_Law()
	{
		return;
	}

	public static void main(String[] args) throws IOException
	{
		Mendel_Law get_info = new Mendel_Law();

		get_info.parse_file();
		get_info.build_tree();
		get_info.print();
	}

	void parse_file() throws IOException
	{
		//Reads through a file and saves the information in an array.
		//TODO: Add error-checking to make sure the file contains 3 numbers only.
		ReadFile read = new ReadFile();
		String population = read.fileToString();

		String[] num;

		num = population.split("\\s");

		genotypes[0] = Double.parseDouble(num[0]);
		genotypes[1] = Double.parseDouble(num[1]);
		genotypes[2] = Double.parseDouble(num[2]);
	}

	void build_tree()
	{

		//Loops through and builds a tree with 3 children for every parent based off the class Node
		for (int i = 0; i < 3; i++)
		{
			Node current = new Node(root);
			for (int j = 0; j < 3; j++)
			{
				new Node(current);
			}
		}

		return;
	}

	void print()
	{
		//Prints the total probability using the event and dominant probability of relevant nodes (dominant probability of parent nodes are not needed).
		DecimalFormat df = new DecimalFormat("#0.00000");

		double total_prob = 0;

		for (int i = 0; i < 3; i++)
		{
			Node temp = root.child_node.get(i);
			for (int j = 0; j < 3; j++)
			{
				total_prob = total_prob + (temp.event_prob * temp.child_node.get(j).event_prob * temp.child_node.get(j).dom_prob);
			}
		}

		System.out.println(df.format(total_prob));
	}
}
