package com.zkrypto.zkwalletWithCustody.global.crypto.constant;

import lombok.Getter;

import java.math.BigInteger;

@Getter
public class TwistedEdwardsCurve {
    private BigInteger prime;
    private BigInteger coefA;
    private BigInteger coefD;
    private AffinePoint g;

    public TwistedEdwardsCurve() {
        this.prime = new BigInteger("21888242871839275222246405745257275088548364400416034343698204186575808495617");
        this.coefA = new BigInteger("1");
        this.coefD = new BigInteger("9706598848417545097372247223557719406784115219466060233080913168975159366771");
        this.g = new AffinePoint(new BigInteger("19698561148652590122159747500897617769866003486955115824547446575314762165298"), new BigInteger("19298250018296453272277890825869354524455968081175474282777126169995084727839"));
    }

    public AffinePoint preprocessBasePoint(AffinePoint p) {
        BigInteger newX = p.getX().mod(prime);
        BigInteger newY = p.getY().mod(prime);
        return new AffinePoint(newX, newY);
    }

    public AffinePoint doubleAffinePoint(AffinePoint p) {
        BigInteger x1y1 = p.getX().multiply(p.getY()).mod(prime);
        BigInteger x2 = p.getX().modPow(BigInteger.valueOf(2), prime);
        BigInteger y2 = p.getY().modPow(BigInteger.valueOf(2), prime);

        BigInteger numX = BigInteger.valueOf(2).multiply(x1y1).mod(prime);
        BigInteger denX = coefA.multiply(x2).add(y2).mod(prime);

        BigInteger numY = y2.subtract(coefA.multiply(x2)).mod(prime);
        BigInteger denY = BigInteger.valueOf(2)
                .subtract(coefA.multiply(x2))
                .subtract(y2)
                .mod(prime);

        BigInteger newX = fieldDivision(numX, denX);
        BigInteger newY = fieldDivision(numY, denY);

        return new AffinePoint(newX, newY);
    }

    public AffinePoint addAffinePoint(AffinePoint p1, AffinePoint p2) {
        BigInteger x1x2 = p1.getX().multiply(p2.getX()).mod(prime);
        BigInteger x1y2 = p1.getX().multiply(p2.getY()).mod(prime);
        BigInteger x2y1 = p2.getX().multiply(p1.getY()).mod(prime);
        BigInteger y1y2 = p1.getY().multiply(p2.getY()).mod(prime);
        BigInteger dxy = coefD.multiply(x1x2).multiply(y1y2).mod(prime);

        BigInteger numX = x1y2.add(x2y1).mod(prime);
        BigInteger denX = BigInteger.ONE.add(dxy).mod(prime);

        BigInteger numY = y1y2.subtract(coefA.multiply(x1x2)).mod(prime);
        BigInteger denY = BigInteger.ONE.subtract(dxy).mod(prime);

        BigInteger newX = fieldDivision(numX, denX);
        BigInteger newY = fieldDivision(numY, denY);

        return new AffinePoint(newX, newY);
    }

    public AffinePoint subAffinePoint(AffinePoint p1, AffinePoint p2) {
        AffinePoint negP2 = new AffinePoint(p2.getX().negate().mod(prime), p2.getY());
        return addAffinePoint(p1, negP2);
    }

    public BigInteger fieldDivision(BigInteger a, BigInteger b) {
        return a.multiply(b.modInverse(prime)).mod(prime);
    }

    public boolean checkPointOnCurve(AffinePoint p) {
        BigInteger x2 = p.getX().modPow(BigInteger.valueOf(2), prime);
        BigInteger y2 = p.getY().modPow(BigInteger.valueOf(2), prime);

        BigInteger lhs = coefA.multiply(x2).add(y2).mod(prime);
        BigInteger rhs = coefD.multiply(x2).multiply(y2).add(BigInteger.ONE).mod(prime);

        return lhs.equals(rhs);
    }

    public AffinePoint computeScalarMul(AffinePoint p, BigInteger exp) {
        AffinePoint result = null;
        AffinePoint base = p;

        for (int i = exp.bitLength() - 1; i >= 0; i--) {
            if (result != null) {
                result = doubleAffinePoint(result);
            }
            if (exp.testBit(i)) {
                if (result == null) {
                    result = base;
                } else {
                    result = addAffinePoint(result, base);
                }
            }
        }
        return result;
    }
}