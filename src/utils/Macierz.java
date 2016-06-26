package utils;

import java.util.Iterator;

/**
 * Klasa s�u��ca do wykonywania podstawowych operacji na macierzach: dodawanie
 * macierzy, odejmowanie, mno�enie, pot�gowanie, transpozycja, wyznaczanie �ladu
 * macierzy, klasa pozwala r�wnie� na wyliczanie wyznacznika macierzy i jej
 * macierzy odwrotnej. Posiada tak�e szereg metod okre�laj�cych typ macierzy:
 * zerowa, kwadratowa, diagonalna, jednostkowa, symetryczna, sko�nosymetryczna,
 * binarna, g�rnotrojk�tna, dolnotr�jk�tna, tr�jk�tna, osobliwa, nieosobliwa,
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
	 * Metoda wyliczaj�ca wyznacznik dla podanej tablicy. Gdy podana tablica
	 * jest tablica 1x1 to jej wyznacznik jet r�wny elementowi tablica[0][0],
	 * je�li jest to tablica 2x2 wyznacznik wyliczany jest ze wzoru:
	 * tablica[0][0] * tablica[1][1] - tablica[0][1] * tablica[1][0], gdy podana
	 * tablica nie jest kwadratowa zwracany jest wyj�tek RuntimeException.
	 *
	 * @param tablica
	 *            Tablica dla kt�rej wyznaczamy wyznacznik
	 * @return wyznacznik macierzy
	 * @throws RuntimeException
	 *             je�li dana macierz nie jest kwadratowa
	 * @see #wyznaczWyznacznik()
	 */
	private double wyznaczWyznacznikMacierzy(double[][] tablica) {
		double wyznacznik = 0;

		if (tablica.length == 1 && tablica[0].length == 1) {
			wyznacznik = tablica[0][0];
		} else if (tablica.length != tablica[0].length) {
			throw new RuntimeException("Nie mo�na wyznaczy� wyznacznika dla macierzy kt�ra nie jest kwadratowa");
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
	 * Metoda wyznaczaj�ca wyznacznik dla danej macierzy.
	 *
	 * @return wyznacznik macierzy
	 * @see #wyznaczWyznacznikMacierzy(double[][])
	 */
	public double wyznaczWyznacznik() {
		return this.wyznaczWyznacznikMacierzy(this.tablica);
	}

	/**
	 * Metoda s�u��ca do dodania do siebie dw�ch macierzy. Macierze s� dodawane
	 * do siebie tylko wtedy gdy maj� takie same wymiary tzn.: maj� tak� sam�
	 * ilo�� kolumn i wierszy, gdy macierze nie spe�niaj� tego warunku wyrzucany
	 * jest wyj�tek: RuntimeException.
	 *
	 * @param macierz
	 *            Macierz dodawana
	 * @throws RuntimeException
	 *             je�li dodawane macierze maj� inne wymiary
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
			throw new RuntimeException("Nie mo�na doda� do siebie macierzy o r�nych wymiarach");
		}
		this.tablica = macierzDodana;
	}

	/**
	 * Metoda s�u��ca do odj�cia od siebie dw�ch macierzy. Macierze mog� by�
	 * odj�te do siebie tylko wtedy gdy maj� takie same wymiary tzn.: maj� tak�
	 * sam� ilo�� kolumn i wierszy, gdy macierze nie spe�niaj� tego warunku
	 * wyrzucany jest wyj�tek: RuntimeException.
	 *
	 * @param macierz
	 *            Macierz odejmowana
	 * @exception RuntimeException
	 *                gdy macierze maj� inne wymiary
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
			throw new RuntimeException("Nie mo�na odj�� od siebie macierzy o r�nych wymiarach");
		}
		this.tablica = macierzOdjeta;
	}

	/**
	 * Metoda s�u��ca do pomno�enia macierzy przez skalar.
	 *
	 * @param skalar
	 *            liczba przez kt�r� wymna�ana jest macierz
	 * @see #pomnozPrzezSkalar(double,double[][])
	 */
	public void pomnozPrzezSkalar(double skalar) {
		this.tablica = this.pomnozPrzezSkalarTablice(skalar, this.tablica);
	}

	/**
	 * Prywatna metoda s�u��ca do pomno�enia podanej tablicy przez skalar.
	 * 
	 * @param skalar
	 *            liczba przez kt�ra mno�ona jest tablica
	 * @param tablica
	 *            przez kt�r� mno�ony jest skalar
	 * @return wymno�ona tablica
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
	 * Metoda zwracaj�ca macierz w postaci tablicy wielowymiarowej.
	 *
	 * @return macierz w formie tablicy wielowymiarowej
	 */
	public double[][] getTablice() {
		return tablica;
	}

	/**
	 * Sprawdza czy macierz jest macierz� zerow�, czyli taka kt�ra sk�ada si�
	 * tylko z samych zer.
	 *
	 * @return true je�li tablica jest zerowa, false je�li nie
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
	 * Metoda sprawdzaj�ca czy dana macierz jest macierz� kwadratow� czyli tak�
	 * kt�ra ma tyle samo kolumn co wierszy.
	 *
	 * @return boolean True jest kwadratowa, false je�li nie jest kwadratowa
	 */
	public boolean isKwadratowa() {
		if (this.tablica[0].length == this.tablica.length) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Metoda s�u��ca do pomno�enia aktualnej macierzy przez macierz podana jako
	 * parametr metody.
	 *
	 * @param macierz
	 *            przez kt�ra mno�ymy aktualn� macierz
	 */
	public void pomnoz(Macierz macierz) {
		double[][] macierzMnozona = macierz.getTablice();
		double[][] macierzPomnozona = new double[this.tablica.length][macierzMnozona[0].length];
		macierzPomnozona = this.wymnozTablice(this.tablica, macierzMnozona);
		this.tablica = macierzPomnozona;
	}

	/**
	 * Sprawdza czy podana tablica jest diagonalna, czyli taka w kt�rej
	 * wszystkie elementy le��ce poza g��wna przek�tna s� zerami.
	 *
	 * @return true je�li tablica jest diagonalna, false je�li nie
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
	 * Metoda sprawdzaj�ca czy dana macierz jest jednostkowa czyli taka w kt�rej
	 * elementy le��ce na g��wnej przek�tnej sa jedynkami a pozosta�e elementy
	 * s� zerami.
	 *
	 * @return true je�li tablica jest jednostkowa, false je�li nie jest
	 */
	public boolean isJednostkowa() {
		if (!this.isDiagonalna()) {
			return false;// tablice nie diagonalne nie mog� by� jednostkowe
		}
		for (int i = 0, j = 0; i < this.tablica.length; i++, j++) {
			if (this.tablica[i][j] != 1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Sprawdza czy macierz jest symetryczna to znaczy taka dla kt�rej elementy
	 * le��ce na pozycjach [i][j] i [j][i] s� sobie r�wne.
	 *
	 * @return true je�li tablica jest symetryczna, false je�li nie
	 */
	public boolean isSymetryczna() {
		if (!this.isKwadratowa()) {
			return false; // tablica nie kwadratowe nie mog� by� symetryczne
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
	 * Sprawdza czy tablica jest sko�nosymetryczna to znaczy �e dla ka�dego
	 * elementu [i][j]=-[j][i]
	 *
	 * @return true je�li tablica jest sko�nosymetryczna false je�li nie jest
	 */
	public boolean isSkosnosymetryczna() {
		if (!this.isKwadratowa()) {
			return false; // tablice nie kwadratowe nie mog� by�
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
	 * Metoda sprawdzaj�ca czy podana macierz jest macierz� binarna czyli tak�
	 * kt�rej elementy s� albo jedynkami albo zerami.
	 * 
	 * @return true je�li macierz jest binarna, false je�li nie jest
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
	 * Metoda przekszta�caj�ca macierz na typ String, do wygenerowanego stringa
	 * dodawane s� dodatkowe formatowania aby macierz by�a czytelniejsza.
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
	 * Metoda s�u��ca do transponowania macierzy.
	 *
	 * @see #transponujTablice(double[][])
	 */
	public void transponuj() {
		this.tablica = this.transponujTablice(this.tablica);
	}

	/**
	 * Prywatna metoda s�u��ca do zamieniania pozycjami wierszy tablicy z jej
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
	 * Metoda sprawdzaj�ca czy dana macierz jest g�rnotr�jk�tna czyli taka
	 * macierz kwadratowa w kt�rej wszystkie elementy poni�ej g��wnej przek�tnej
	 * s� zerami.
	 * 
	 * @return true je�li macierz jest g�rnotr�jk�tna, false je�li nie jest
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
	 * Metoda sprawdzaj�ca czy dana macierz jest dolnotr�jk�tna czyli taka
	 * macierz kwadratowa w kt�rej wszystkie elementy powy�ej g��wnej przek�tnej
	 * s� zerami.
	 *
	 * @return true je�li macierz jest dolnotr�jk�tna, false je�li nie jest
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
	 * Sprawdza czy dana macierz jest tr�jk�tna czyli taka dla kt�rej elementy
	 * le��ce poni�ej lub powy�ej g��wnej przek�tnej s� zerami.
	 *
	 * @return true je�li macierz jest tr�jk�tna, false je�li nie jest
	 */
	public boolean isTrojkatna() {
		if (!this.isDolnoTrojkatna() && !this.isGornoTrojkatna()) {
			return false;
		}
		return true;
	}

	/**
	 * Sprawdza czy dana macierz jest osobliwa czyli taka dla kt�rej wyznacznik
	 * jest r�wny 0.
	 * 
	 * @return true je�li macierz jest osobliwa, false je�li nie jest
	 */
	public boolean isOsobliwa() {
		if (this.wyznaczWyznacznik() != 0) {
			return false;
		}
		return true;
	}

	/**
	 * Sprawdza czy dana macierz jest nieosobliwa czyli taka dla kt�rej
	 * wyznacznik jest r�ny od 0.
	 *
	 * @return true je�li macierz jest nieosobliwa, false je�li nie jest
	 */
	public boolean isNieosobliwa() {
		if (this.wyznaczWyznacznik() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * Metod� sprawdzaj�ca czy dana macierz jest skalarna czyli taka macierz
	 * kt�ra jest macierz� diagonaln� i r�wnocze�nie wszystkie jej elementy
	 * le��ce na g��wnej przek�tnej s� sobie r�wne.
	 * 
	 * @return true je�li macierz jest skalarna, false je�li nie jest
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
	 * Sprawdza czy dana macierz jest idempotentna czyli tak� macierz kwadratowa
	 * dla kt�rej jej kwadrat jest r�wny jej samej A=A^2.
	 * 
	 * @return true je�li macierz jest idempotentna, false je�li nie jest
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
		// Por�wnywanie macierzy A i A^2
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
	 * Prywatna metoda jej zadaniem jest podnoszenie do pot�gi podanej tablicy,
	 * metoda wykorzystuje przy pot�gowaniu algorytm szybkiego pot�gowania.
	 *
	 * @param tab
	 *            tablica kt�ra jest pot�gowana
	 * @param wykladnik
	 *            warto�� do kt�rej podnoszona jest tablica
	 * @return tablica podniesiona do podanej pot�gi
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
	 * Metoda s�u��ca do podnoszenia macierzy do podanej pot�gi, w przypadku gdy
	 * macierz nie jest kwadratowa lub podany wyk�adnik jest ujemny wyrzucany
	 * jest wyj�tek RuntimeException.
	 *
	 * @param wykladnik
	 *            warto�� pot�gi do jakiej podnoszona jest macierz
	 * @exception RuntimeException
	 *                macierz nie jest kwadratowa lub wyk�adnik ujemny
	 * @see #isKwadratowa()
	 */
	public void podniesDoPotegi(int wykladnik) {
		if (!this.isKwadratowa()) {
			throw new RuntimeException("Nie mo�na pot�gowa� macierzy kt�re nie s� kwadratowe");
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
				throw new RuntimeException("Wyk�adnik nie mo�e by� ujemny");
			}
		}
	}

	/**
	 * Metoda s�u��ca do pomno�enia dw�ch tablic: tab1 i tab2, tablice te mo�na
	 * pomno�y� przez siebie pod warunkiem ze ilo�� kolumn tablicy tab1 jest
	 * taka sama jak ilo�� wierszy tablicy tab2, w przeciwnym wypadku wyrzucany
	 * jest wyj�tek RuntimeException.
	 * 
	 * @param tab1
	 *            pierwsza tablica
	 * @param tab2
	 *            druga tablica
	 * @return tablica b�d�ca iloczynem tablic tab1 i tab2
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
			throw new RuntimeException("Podane tablice maj� niew�asciwe wymiary");
		}
		return macierzPomnozona;
	}

	/**
	 * Sprawdza czy macierz jest kolumnowa czyli taka kt�ra posiada jedn�
	 * kolumn�.
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
	 * Sprawdza czy tablica jest wierszowa czyli taka kt�ra posiada jeden
	 * wiersz.
	 *
	 * @return boolean true je�li jest wierszowa, false je�li nie jest
	 */
	public boolean isWierszowa() {
		if (this.tablica.length == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Metoda wyliczaj�ca �lad macierzy czyli sum� element�w na g��wnej
	 * przek�tnej macierzy, macierz dla kt�rej wyznaczany jest �lad musi by�
	 * macierz� kwadratowa.
	 *
	 * @return �lad macierzy
	 */
	public double wyznaczSladMacierzy() {
		double suma = 0;
		if (!this.isKwadratowa()) {
			throw new RuntimeException("Nie mo�na wyznaczy� �ladu dla macierzy nie kwadratowej");
		} else {
			for (int i = 0; i < this.tablica.length; i++) {
				suma += this.tablica[i][i];
			}
		}
		return suma;
	}

	/**
	 * Metoda sprawdzaj�ca czy dana macierz jest antydiagonalna, czyli taka
	 * kt�ra zawiera same zera opr�cz element�w na przek�tnej biegn�cej od
	 * prawego g�rnego wierzcho�ka do lewego dolnego. Macierz mo�e by�
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
	 * Metoda s�u��ca do wyznaczania macierzy odwrotnej do podanej w
	 * konstruktorze klasy tablicy. W pierwszej kolejno�ci sprawdzane jest czy
	 * dana tablica jest kwadratowa, tylko dla takich tablic mo�na wyznaczy�
	 * macierz odwrotna, je�li nie jest kwadratowa wyrzucany jest wyj�tek
	 * RuntimeException. Dla macierzy kwadratowych sk�adaj�cych si� tylko z
	 * jednego elementu macierz odwrotna jest r�wna 1/(wyznacznik tablicy), dla
	 * macierzy o wi�kszej ilo�ci element�w wyznaczana jest macierz do��czona a
	 * sam� macierz odwrotna wyznacza si� jako 1/(wyznacznik tablicy)*(macierz
	 * do��czona).
	 * 
	 * @return macierz odwrotna
	 */
	public Macierz wyznaczMacierzOdwrotna() {
		if (!this.isKwadratowa()) {
			throw new RuntimeException("Nie mo�na wyznaczy� macierzy odwrotnej dla macierzy kt�ra nie jest kwadratowa");
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