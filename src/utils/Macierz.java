package utils;

import java.util.Iterator;

/**
 * Klasa s³u¿¹ca do wykonywania podstawowych operacji na macierzach: dodawanie
 * macierzy, odejmowanie, mno¿enie, potêgowanie, transpozycja, wyznaczanie œladu
 * macierzy, klasa pozwala równie¿ na wyliczanie wyznacznika macierzy i jej
 * macierzy odwrotnej. Posiada tak¿e szereg metod okreœlaj¹cych typ macierzy:
 * zerowa, kwadratowa, diagonalna, jednostkowa, symetryczna, skoœnosymetryczna,
 * binarna, górnotrojk¹tna, dolnotrójk¹tna, trójk¹tna, osobliwa, nieosobliwa,
 * skalarna, kolumnowa, wierszowa czy antydiagonlna.
 *
 * @author http://www.darmoweskrypty.linuxpl.info
 * @version 1.0
 */
public class Macierz {

	private double[][] tablica;

	Macierz(double[][] m1) {
		this.tablica = m1;
	}

	/**
	 * Metoda wyliczaj¹ca wyznacznik dla podanej tablicy. Gdy podana tablica
	 * jest tablica 1x1 to jej wyznacznik jet równy elementowi tablica[0][0],
	 * jeœli jest to tablica 2x2 wyznacznik wyliczany jest ze wzoru:
	 * tablica[0][0] * tablica[1][1] - tablica[0][1] * tablica[1][0], gdy podana
	 * tablica nie jest kwadratowa zwracany jest wyj¹tek RuntimeException.
	 *
	 * @param tablica
	 *            Tablica dla której wyznaczamy wyznacznik
	 * @return wyznacznik macierzy
	 * @throws RuntimeException
	 *             jeœli dana macierz nie jest kwadratowa
	 * @see #wyznaczWyznacznik()
	 */
	private double wyznaczWyznacznikMacierzy(double[][] tablica) {
		double wyznacznik = 0;

		if (tablica.length == 1 && tablica[0].length == 1) {
			wyznacznik = tablica[0][0];
		} else if (tablica.length != tablica[0].length) {
			throw new RuntimeException("Nie mo¿na wyznaczyæ wyznacznika dla macierzy która nie jest kwadratowa");
		} else if (tablica.length == 2 && tablica[0].length == 2) {
			wyznacznik = (tablica[0][0] * tablica[1][1] - tablica[0][1] * tablica[1][0]);
		} else {
			double[][] nTab = new double[tablica.length + (tablica.length - 1)][tablica[0].length];
			for (int i = 0, _i = 0; i < nTab.length; i++, _i++) {
				for (int j = 0; j < tablica[0].length; j++) {
					if (_i < tablica.length && j < tablica[0].length) {
						nTab[i][j] = tablica[_i][j];
					} else {
						_i = 0;
						nTab[i][j] = tablica[_i][j];
					}
				}
			}

			double iloczyn = 1;
			int _i;

			for (int i = 0; i < tablica.length; i++) {
				_i = i;
				for (int j = 0; j < tablica[0].length; j++) {
					iloczyn *= nTab[_i][j];
					_i++;
				}
				wyznacznik += iloczyn;
				iloczyn = 1;
			}

			iloczyn = 1;
			for (int i = 0; i < tablica.length; i++) {
				_i = i;
				for (int j = tablica[0].length - 1; j >= 0; j--) {
					iloczyn *= nTab[_i][j];
					_i++;
				}
				wyznacznik -= iloczyn;
				iloczyn = 1;
			}
		}
		return wyznacznik;
	}

	/**
	 * Metoda wyznaczaj¹ca wyznacznik dla danej macierzy.
	 *
	 * @return wyznacznik macierzy
	 * @see #wyznaczWyznacznikMacierzy(double[][])
	 */
	public double wyznaczWyznacznik() {
		return this.wyznaczWyznacznikMacierzy(this.tablica);
	}

	/**
	 * Metoda s³u¿¹ca do dodania do siebie dwóch macierzy. Macierze s¹ dodawane
	 * do siebie tylko wtedy gdy maj¹ takie same wymiary tzn.: maj¹ tak¹ sam¹
	 * iloœæ kolumn i wierszy, gdy macierze nie spe³niaj¹ tego warunku wyrzucany
	 * jest wyj¹tek: RuntimeException.
	 *
	 * @param macierz
	 *            Macierz dodawana
	 * @throws RuntimeException
	 *             jeœli dodawane macierze maj¹ inne wymiary
	 */
	public void dodajMacierz(Macierz macierz) {
		double[][] tablicaDoDodania = macierz.getTablice();
		double[][] macierzDodana = null;
		if (this.tablica.length == tablicaDoDodania.length && this.tablica[0].length == tablicaDoDodania[0].length) {
			macierzDodana = new double[this.tablica.length][this.tablica[0].length];
			for (int i = 0; i < this.tablica.length; i++) {
				for (int j = 0; j < this.tablica[0].length; j++) {
					macierzDodana[i][j] = (this.tablica[i][j] + tablicaDoDodania[i][j]);
				}
			}
		} else {
			throw new RuntimeException("Nie mo¿na dodaæ do siebie macierzy o ró¿nych wymiarach");
		}
		this.tablica = macierzDodana;
	}

	/**
	 * Metoda s³u¿¹ca do odjêcia od siebie dwóch macierzy. Macierze mog¹ byæ
	 * odjête do siebie tylko wtedy gdy maj¹ takie same wymiary tzn.: maj¹ tak¹
	 * sam¹ iloœæ kolumn i wierszy, gdy macierze nie spe³niaj¹ tego warunku
	 * wyrzucany jest wyj¹tek: RuntimeException.
	 *
	 * @param macierz
	 *            Macierz odejmowana
	 * @exception RuntimeException
	 *                gdy macierze maj¹ inne wymiary
	 */
	public void odejmijMacierz(Macierz macierz) {
		double[][] tablicaDoOdjecia = macierz.getTablice();
		double[][] macierzOdjeta = null;
		if (this.tablica.length == tablicaDoOdjecia.length && this.tablica[0].length == tablicaDoOdjecia[0].length) {
			macierzOdjeta = new double[this.tablica.length][this.tablica[0].length];
			for (int i = 0; i < this.tablica.length; i++) {
				for (int j = 0; j < this.tablica[0].length; j++) {
					macierzOdjeta[i][j] = (this.tablica[i][j] - tablicaDoOdjecia[i][j]);
				}
			}
		} else {
			throw new RuntimeException("Nie mo¿na odj¹æ od siebie macierzy o ró¿nych wymiarach");
		}
		this.tablica = macierzOdjeta;
	}

	/**
	 * Metoda s³u¿¹ca do pomno¿enia macierzy przez skalar.
	 *
	 * @param skalar
	 *            liczba przez któr¹ wymna¿ana jest macierz
	 * @see #pomnozPrzezSkalar(double,double[][])
	 */
	public void pomnozPrzezSkalar(double skalar) {
		this.tablica = this.pomnozPrzezSkalarTablice(skalar, this.tablica);
	}

	/**
	 * Prywatna metoda s³u¿¹ca do pomno¿enia podanej tablicy przez skalar.
	 * 
	 * @param skalar
	 *            liczba przez która mno¿ona jest tablica
	 * @param tablica
	 *            przez któr¹ mno¿ony jest skalar
	 * @return wymno¿ona tablica
	 */
	private double[][] pomnozPrzezSkalarTablice(double skalar, double[][] tablica) {
		double[][] macierzPomnozona = new double[tablica.length][tablica[0].length];
		for (int i = 0; i < tablica.length; i++) {
			for (int j = 0; j < tablica[0].length; j++) {
				macierzPomnozona[i][j] = (tablica[i][j] * skalar);
			}
		}
		return macierzPomnozona;
	}

	/**
	 * Metoda zwracaj¹ca macierz w postaci tablicy wielowymiarowej.
	 *
	 * @return macierz w formie tablicy wielowymiarowej
	 */
	public double[][] getTablice() {
		return tablica;
	}

	/**
	 * Sprawdza czy macierz jest macierz¹ zerow¹, czyli taka która sk³ada siê
	 * tylko z samych zer.
	 *
	 * @return true jeœli tablica jest zerowa, false jeœli nie
	 */
	public boolean isZerowa() {
		for (int i = 0; i < this.tablica.length; i++) {
			for (int j = 0; j < this.tablica[0].length; j++) {
				if (this.tablica[i][j] != 0) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Metoda sprawdzaj¹ca czy dana macierz jest macierz¹ kwadratow¹ czyli tak¹
	 * która ma tyle samo kolumn co wierszy.
	 *
	 * @return boolean True jest kwadratowa, false jeœli nie jest kwadratowa
	 */
	public boolean isKwadratowa() {
		if (this.tablica[0].length == this.tablica.length) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Metoda s³u¿¹ca do pomno¿enia aktualnej macierzy przez macierz podana jako
	 * parametr metody.
	 *
	 * @param macierz
	 *            przez która mno¿ymy aktualn¹ macierz
	 */
	public void pomnoz(Macierz macierz) {
		double[][] macierzMnozona = macierz.getTablice();
		double[][] macierzPomnozona = new double[this.tablica.length][macierzMnozona[0].length];
		macierzPomnozona = this.wymnozTablice(this.tablica, macierzMnozona);
		this.tablica = macierzPomnozona;
	}

	/**
	 * Sprawdza czy podana tablica jest diagonalna, czyli taka w której
	 * wszystkie elementy le¿¹ce poza g³ówna przek¹tna s¹ zerami.
	 *
	 * @return true jeœli tablica jest diagonalna, false jeœli nie
	 */
	public boolean isDiagonalna() {
		if (!this.isKwadratowa()) {
			return false;
		}
		for (int i = 0; i < this.tablica.length; i++) {
			for (int j = 0; j < this.tablica[0].length; j++) {
				if (i != j && this.tablica[i][j] != 0) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Metoda sprawdzaj¹ca czy dana macierz jest jednostkowa czyli taka w której
	 * elementy le¿¹ce na g³ównej przek¹tnej sa jedynkami a pozosta³e elementy
	 * s¹ zerami.
	 *
	 * @return true jeœli tablica jest jednostkowa, false jeœli nie jest
	 */
	public boolean isJednostkowa() {
		if (!this.isDiagonalna()) {
			return false;// tablice nie diagonalne nie mog¹ byæ jednostkowe
		}
		for (int i = 0, j = 0; i < this.tablica.length; i++, j++) {
			if (this.tablica[i][j] != 1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Sprawdza czy macierz jest symetryczna to znaczy taka dla której elementy
	 * le¿¹ce na pozycjach [i][j] i [j][i] s¹ sobie równe.
	 *
	 * @return true jeœli tablica jest symetryczna, false jeœli nie
	 */
	public boolean isSymetryczna() {
		if (!this.isKwadratowa()) {
			return false; // tablica nie kwadratowe nie mog¹ byæ symetryczne
		} else {
			for (int i = 0; i < this.tablica.length; i++) {
				for (int j = 0; j < this.tablica[0].length; j++) {
					if (this.tablica[i][j] != this.tablica[j][i]) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Sprawdza czy tablica jest skoœnosymetryczna to znaczy ¿e dla ka¿dego
	 * elementu [i][j]=-[j][i]
	 *
	 * @return true jeœli tablica jest skoœnosymetryczna false jeœli nie jest
	 */
	public boolean isSkosnosymetryczna() {
		if (!this.isKwadratowa()) {
			return false; // tablice nie kwadratowe nie mog¹ byæ
							// skosnosymetryczne
		} else {
			for (int i = 0; i < this.tablica.length; i++) {
				for (int j = 0; j < this.tablica[0].length; j++) {
					if (this.tablica[i][j] != (-this.tablica[j][i])) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Metoda sprawdzaj¹ca czy podana macierz jest macierz¹ binarna czyli tak¹
	 * której elementy s¹ albo jedynkami albo zerami.
	 * 
	 * @return true jeœli macierz jest binarna, false jeœli nie jest
	 */
	public boolean isBinarna() {
		for (int i = 0; i < this.tablica.length; i++) {
			for (int j = 0; j < this.tablica[0].length; j++) {
				if (this.tablica[i][j] == 0 || this.tablica[i][j] == 1) {
				} else {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Metoda przekszta³caj¹ca macierz na typ String, do wygenerowanego stringa
	 * dodawane s¹ dodatkowe formatowania aby macierz by³a czytelniejsza.
	 *
	 * @return tablica w formie obiektu String
	 */
	@Override
	public String toString() {
		String temp = "";
		for (int i = 0; i < this.tablica.length; i++) {
			temp += "|";
			for (int j = 0; j < this.tablica[0].length; j++) {
				temp += " " + (int) (this.tablica[i][j]);
			}
			temp += " |\n";

		}
		return temp;
	}

	/**
	 * Metoda s³u¿¹ca do transponowania macierzy.
	 *
	 * @see #transponujTablice(double[][])
	 */
	public void transponuj() {
		this.tablica = this.transponujTablice(this.tablica);
	}

	/**
	 * Prywatna metoda s³u¿¹ca do zamieniania pozycjami wierszy tablicy z jej
	 * kolumnami.
	 *
	 * @param tablica
	 *            do transpozycji
	 * @return tablica z zamienionymi kolumnami i wierszami
	 * @see #transponuj()
	 */
	private double[][] transponujTablice(double[][] tablica) {
		double[][] macierzTransponowana = new double[tablica[0].length][tablica.length];
		for (int i = 0; i < tablica.length; i++) {
			for (int j = 0; j < tablica[0].length; j++) {
				macierzTransponowana[j][i] = tablica[i][j];
			}
		}
		return macierzTransponowana;
	}

	/**
	 * Metoda sprawdzaj¹ca czy dana macierz jest górnotrójk¹tna czyli taka
	 * macierz kwadratowa w której wszystkie elementy poni¿ej g³ównej przek¹tnej
	 * s¹ zerami.
	 * 
	 * @return true jeœli macierz jest górnotrójk¹tna, false jeœli nie jest
	 */
	public boolean isGornoTrojkatna() {
		if (!this.isKwadratowa()) {
			return false;
		}
		for (int i = 0; i < this.tablica.length; i++) {
			for (int j = 0; j < this.tablica[0].length; j++) {
				if (i > j && this.tablica[i][j] != 0) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Metoda sprawdzaj¹ca czy dana macierz jest dolnotrójk¹tna czyli taka
	 * macierz kwadratowa w której wszystkie elementy powy¿ej g³ównej przek¹tnej
	 * s¹ zerami.
	 *
	 * @return true jeœli macierz jest dolnotrójk¹tna, false jeœli nie jest
	 */
	public boolean isDolnoTrojkatna() {
		if (!this.isKwadratowa()) {
			return false;
		}
		for (int i = 0; i < this.tablica.length; i++) {
			for (int j = 0; j < this.tablica[0].length; j++) {
				if (j > i && this.tablica[i][j] != 0) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Sprawdza czy dana macierz jest trójk¹tna czyli taka dla której elementy
	 * le¿¹ce poni¿ej lub powy¿ej g³ównej przek¹tnej s¹ zerami.
	 *
	 * @return true jeœli macierz jest trójk¹tna, false jeœli nie jest
	 */
	public boolean isTrojkatna() {
		if (!this.isDolnoTrojkatna() && !this.isGornoTrojkatna()) {
			return false;
		}
		return true;
	}

	/**
	 * Sprawdza czy dana macierz jest osobliwa czyli taka dla której wyznacznik
	 * jest równy 0.
	 * 
	 * @return true jeœli macierz jest osobliwa, false jeœli nie jest
	 */
	public boolean isOsobliwa() {
		if (this.wyznaczWyznacznik() != 0) {
			return false;
		}
		return true;
	}

	/**
	 * Sprawdza czy dana macierz jest nieosobliwa czyli taka dla której
	 * wyznacznik jest ró¿ny od 0.
	 *
	 * @return true jeœli macierz jest nieosobliwa, false jeœli nie jest
	 */
	public boolean isNieosobliwa() {
		if (this.wyznaczWyznacznik() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * Metod¹ sprawdzaj¹ca czy dana macierz jest skalarna czyli taka macierz
	 * która jest macierz¹ diagonaln¹ i równoczeœnie wszystkie jej elementy
	 * le¿¹ce na g³ównej przek¹tnej s¹ sobie równe.
	 * 
	 * @return true jeœli macierz jest skalarna, false jeœli nie jest
	 * @see #isDiagonalna()
	 */
	public boolean isSkalarna() {
		if (!this.isDiagonalna()) {
			return false;
		}
		double element = this.tablica[0][0];
		for (int i = 0, j = 0; i < this.tablica.length; i++, j++) {
			if (this.tablica[i][j] != element) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Sprawdza czy dana macierz jest idempotentna czyli tak¹ macierz kwadratowa
	 * dla której jej kwadrat jest równy jej samej A=A^2.
	 * 
	 * @return true jeœli macierz jest idempotentna, false jeœli nie jest
	 */
	public boolean isIdempotentna() {
		if (!this.isKwadratowa()) {
			return false;
		}
		double[][] macierzKwadratowa = new double[this.tablica.length][this.tablica[0].length];
		for (int i = 0; i < this.tablica.length; i++) {
			for (int j = 0; j < this.tablica[0].length; j++) {
				double temp = 0;
				for (int w = 0; w < this.tablica.length; w++) {
					temp += this.tablica[i][w] * this.tablica[w][j];
				}
				macierzKwadratowa[i][j] = temp;
			}
		}
		// Porównywanie macierzy A i A^2
		for (int i = 0; i < this.tablica.length; i++) {
			for (int j = 0; j < this.tablica[0].length; j++) {
				if (this.tablica[i][j] != macierzKwadratowa[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Prywatna metoda jej zadaniem jest podnoszenie do potêgi podanej tablicy,
	 * metoda wykorzystuje przy potêgowaniu algorytm szybkiego potêgowania.
	 *
	 * @param tab
	 *            tablica która jest potêgowana
	 * @param wykladnik
	 *            wartoœæ do której podnoszona jest tablica
	 * @return tablica podniesiona do podanej potêgi
	 * @see #podniesDoPotegi(int)
	 */
	private double[][] potegoj(double[][] tab, int wykladnik) {
		if (wykladnik == 0) {
			double[][] temp = new double[tab.length][tab[0].length];
			for (int i = 0; i < temp.length; i++) {
				for (int j = 0; j < temp[0].length; j++) {
					if (i == j) {
						temp[i][j] = 1;
					} else {
						temp[i][j] = 0;
					}
				}
			}
			return temp;
		} else if (wykladnik % 2 != 0) {
			return this.wymnozTablice(tab, this.potegoj(tab, wykladnik - 1));
		} else {
			double[][] temp = this.potegoj(tab, wykladnik / 2);
			return this.wymnozTablice(temp, temp);
		}
	}

	/**
	 * Metoda s³u¿¹ca do podnoszenia macierzy do podanej potêgi, w przypadku gdy
	 * macierz nie jest kwadratowa lub podany wyk³adnik jest ujemny wyrzucany
	 * jest wyj¹tek RuntimeException.
	 *
	 * @param wykladnik
	 *            wartoœæ potêgi do jakiej podnoszona jest macierz
	 * @exception RuntimeException
	 *                macierz nie jest kwadratowa lub wyk³adnik ujemny
	 * @see #isKwadratowa()
	 */
	public void podniesDoPotegi(int wykladnik) {
		if (!this.isKwadratowa()) {
			throw new RuntimeException("Nie mo¿na potêgowaæ macierzy które nie s¹ kwadratowe");
		} else {
			if (wykladnik >= 2) {
				if (wykladnik % 2 == 0) {
					this.tablica = this.potegoj(this.tablica, wykladnik);
				}
			} else if (wykladnik == 1) {// A^1=A
			} else if (wykladnik == 0) {// tablica jednostkowa A^0=I
				for (int i = 0; i < this.tablica.length; i++) {
					for (int j = 0; j < this.tablica[0].length; j++) {
						this.tablica[i][j] = 1;
					}
				}
			} else {
				throw new RuntimeException("Wyk³adnik nie mo¿e byæ ujemny");
			}
		}
	}

	/**
	 * Metoda s³u¿¹ca do pomno¿enia dwóch tablic: tab1 i tab2, tablice te mo¿na
	 * pomno¿yæ przez siebie pod warunkiem ze iloœæ kolumn tablicy tab1 jest
	 * taka sama jak iloœæ wierszy tablicy tab2, w przeciwnym wypadku wyrzucany
	 * jest wyj¹tek RuntimeException.
	 * 
	 * @param tab1
	 *            pierwsza tablica
	 * @param tab2
	 *            druga tablica
	 * @return tablica bêd¹ca iloczynem tablic tab1 i tab2
	 */
	private double[][] wymnozTablice(double[][] tab1, double[][] tab2) {
		double[][] macierzPomnozona = new double[tab1.length][tab2[0].length];
		if (tab1[0].length == tab2.length) {
			for (int i = 0; i < tab1.length; i++) {// ilosc wierszy tab1
				for (int j = 0; j < tab2[0].length; j++) { // ilosc kolumn tab2
					double temp = 0;
					for (int w = 0; w < tab2.length; w++) { // ilosc wierszy
															// tab2
						temp += tab1[i][w] * tab2[w][j];
					}
					macierzPomnozona[i][j] = temp;
				}
			}
		} else {
			throw new RuntimeException("Podane tablice maj¹ niew³asciwe wymiary");
		}
		return macierzPomnozona;
	}

	/**
	 * Sprawdza czy macierz jest kolumnowa czyli taka która posiada jedn¹
	 * kolumnê.
	 *
	 * @return true dla macierzy kolumnowej, false dla macierzy nie kolumnowej
	 */
	public boolean isKolumnowa() {
		if (this.tablica[0].length == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sprawdza czy tablica jest wierszowa czyli taka która posiada jeden
	 * wiersz.
	 *
	 * @return boolean true jeœli jest wierszowa, false jeœli nie jest
	 */
	public boolean isWierszowa() {
		if (this.tablica.length == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Metoda wyliczaj¹ca œlad macierzy czyli sumê elementów na g³ównej
	 * przek¹tnej macierzy, macierz dla której wyznaczany jest œlad musi byæ
	 * macierz¹ kwadratowa.
	 *
	 * @return œlad macierzy
	 */
	public double wyznaczSladMacierzy() {
		double suma = 0;
		if (!this.isKwadratowa()) {
			throw new RuntimeException("Nie mo¿na wyznaczyæ œladu dla macierzy nie kwadratowej");
		} else {
			for (int i = 0; i < this.tablica.length; i++) {
				suma += this.tablica[i][i];
			}
		}
		return suma;
	}

	/**
	 * Metoda sprawdzaj¹ca czy dana macierz jest antydiagonalna, czyli taka
	 * która zawiera same zera oprócz elementów na przek¹tnej biegn¹cej od
	 * prawego górnego wierzcho³ka do lewego dolnego. Macierz mo¿e byæ
	 * antydiagonalna pod warunkiem ze jest kwadratowa.
	 *
	 * @return true jesli jest antydiagonalna, false jesli nie jest
	 *         antydiagonalna
	 */
	public boolean isAntydiagonalna() {
		if (!this.isKwadratowa()) {
			return false;
		}
		int temp = this.tablica[0].length - 1;
		for (int i = 0; i < this.tablica.length; i++) {
			for (int j = 0; j < this.tablica[0].length; j++) {
				if (j != temp && this.tablica[i][j] != 0) {
					return false;
				}
			}
			temp--;
		}
		return true;
	}

	/**
	 * Metoda s³u¿¹ca do wyznaczania macierzy odwrotnej do podanej w
	 * konstruktorze klasy tablicy. W pierwszej kolejnoœci sprawdzane jest czy
	 * dana tablica jest kwadratowa, tylko dla takich tablic mo¿na wyznaczyæ
	 * macierz odwrotna, jeœli nie jest kwadratowa wyrzucany jest wyj¹tek
	 * RuntimeException. Dla macierzy kwadratowych sk³adaj¹cych siê tylko z
	 * jednego elementu macierz odwrotna jest równa 1/(wyznacznik tablicy), dla
	 * macierzy o wiêkszej iloœci elementów wyznaczana jest macierz do³¹czona a
	 * sam¹ macierz odwrotna wyznacza siê jako 1/(wyznacznik tablicy)*(macierz
	 * do³¹czona).
	 * 
	 * @return macierz odwrotna
	 */
	public Macierz wyznaczMacierzOdwrotna() {
		if (!this.isKwadratowa()) {
			throw new RuntimeException("Nie mo¿na wyznaczyæ macierzy odwrotnej dla macierzy która nie jest kwadratowa");
		} else {
			double[][] macierzOdwrotna;

			if (this.tablica.length == 1) {
				macierzOdwrotna = new double[1][1];
				macierzOdwrotna[0][0] = 1 / this.wyznaczWyznacznik();
				return new Macierz(macierzOdwrotna);
			} else {
				macierzOdwrotna = new double[this.tablica.length][this.tablica[0].length];
				double[][] macierzDolaczona = new double[this.tablica.length][this.tablica[0].length];
				for (int i = 0; i < this.tablica.length; i++) {
					for (int j = 0; j < this.tablica[0].length; j++) {
						double[][] temp = new double[this.tablica.length - 1][this.tablica[0].length - 1];
						int a = 0, b = 0;
						for (int w = 0; w < this.tablica.length; w++) {
							for (int z = 0; z < this.tablica[0].length; z++) {
								if (w != i && z != j) {
									if (b >= temp.length) {
										b = 0;
										a++;
									}
									temp[a][b] = this.tablica[w][z];
									b++;
								}
							}
						}

						double wyznacznikTemp = this.wyznaczWyznacznikMacierzy(temp);

						if ((i + j) % 2 != 0) {// Niparzyste czyli zmiana znaku
												// wyznacznika
							if (wyznacznikTemp > 0) {
								wyznacznikTemp -= 2 * wyznacznikTemp;
							} else {
								wyznacznikTemp -= 2 * wyznacznikTemp;
							}
						} else {
						}
						macierzDolaczona[i][j] = wyznacznikTemp;
					}
				}
				macierzDolaczona = this.transponujTablice(macierzDolaczona);
				macierzOdwrotna = this.pomnozPrzezSkalarTablice(1 / this.wyznaczWyznacznik(), macierzDolaczona);
				return new Macierz(macierzOdwrotna);
			}
		}
	}

	public double getMax() {
		double max = -1;
		for (double[] ds : tablica) {
			for (double d : ds) {
				if (max < d && d > 0) {
					max = d;
				}

			}
		}
		return max;

	}
}